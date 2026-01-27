package zzik2.barched.mixin.client.renderer;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.item.ItemStackBridge;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow
    protected abstract void applyItemArmAttackTransform(PoseStack arg, HumanoidArm arg2, float g);

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;", ordinal = 0, shift = At.Shift.BEFORE))
    private void barched$renderArmWithItemUse(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci, @Local(name = "humanoidArm") HumanoidArm humanoidArm, @Local(name = "q") int q) {
        if (itemStack.getUseAnimation() == Barched.UseAnim.BARCHED$SPEAR) {
            poseStack.translate((float)q * 0.56F, -0.52F, -0.72F);
            float l = (float)itemStack.getUseDuration(abstractClientPlayer) - ((float)abstractClientPlayer.getUseItemRemainingTicks() - f + 1.0F);
            SpearAnimations.firstPersonUse(((LivingEntityBridge) abstractClientPlayer).getTicksSinceLastKineticHitFeedback(f), poseStack, l, humanoidArm, itemStack);
        }
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 12))
    private void barched$renderArmWithItemTranslate(PoseStack poseStack, float x, float y, float z, @Local(argsOnly = true) ItemStack itemStack) {
        SwingAnimationType type = ((ItemStackBridge) (Object) itemStack).getSwingAnimation().type();
        if (type != SwingAnimationType.STAB) {
            poseStack.translate(x, y, z);
        }
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 1))
    private void barched$renderArmWithItemAttack(ItemInHandRenderer instance, PoseStack poseStack, HumanoidArm humanoidArm, float h, @Local(argsOnly = true) ItemStack itemStack) {
        SwingAnimationType type = ((ItemStackBridge) (Object) itemStack).getSwingAnimation().type();
        int q = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
        if (type == SwingAnimationType.STAB) {
            SpearAnimations.firstPersonAttack(h, poseStack, q, humanoidArm);
        } else {
            this.applyItemArmAttackTransform(poseStack, humanoidArm, h);
        }
    }
}
