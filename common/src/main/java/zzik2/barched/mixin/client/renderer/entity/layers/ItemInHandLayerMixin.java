package zzik2.barched.mixin.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.item.ItemStackBridge;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {

    public ItemInHandLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER))
    private void barched$renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        float tick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        if (livingEntity.getAttackAnim(tick) > 0.0F && livingEntity.getMainArm() == humanoidArm && ((ItemStackBridge) (Object) itemStack).getSwingAnimation().type() == SwingAnimationType.STAB) {
            SpearAnimations.thirdPersonAttackItem(livingEntity, poseStack);
        }

        float f = ((LivingEntityBridge) livingEntity).barched$ticksUsingItem(humanoidArm);
        if (f != 0.0F) {
            //TODO: add condition if is spear
            SpearAnimations.thirdPersonUseItem(livingEntity, poseStack, f, humanoidArm, itemStack);
        }
    }
}
