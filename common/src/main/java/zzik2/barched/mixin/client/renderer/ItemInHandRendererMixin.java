package zzik2.barched.mixin.client.renderer;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.entity.PlayerBridge;
import zzik2.barched.bridge.item.ItemStackBridge;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Unique private AbstractClientPlayer barched$abstractClientPlayer;
    @Unique private InteractionHand barched$interactionHand;
    @Unique private PoseStack barched$poseStack;
    @Unique private float barched$f;

    @Unique private boolean barched$use;

    @Shadow
    protected abstract void applyItemArmAttackTransform(PoseStack arg, HumanoidArm arg2, float g);

    @Shadow
    protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void barched$capture(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci) {
        this.barched$abstractClientPlayer = abstractClientPlayer;
        this.barched$interactionHand = interactionHand;
        this.barched$poseStack = poseStack;
        this.barched$f = f;
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;", ordinal = 0))
    private UseAnim barched$renderArmWithItem(ItemStack instance) {
        if (instance.getUseAnimation() == Barched.UseAnim.BARCHED$SPEAR) {
            boolean bl = barched$interactionHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidArm = bl ? barched$abstractClientPlayer.getMainArm() : barched$abstractClientPlayer.getMainArm().getOpposite();
            boolean bl2 = humanoidArm == HumanoidArm.RIGHT;
            int q = bl2 ? 1 : -1;

            barched$poseStack.translate((float)q * 0.56F, -0.52F, -0.72F);
            float l = (float)instance.getUseDuration(barched$abstractClientPlayer) - ((float)barched$abstractClientPlayer.getUseItemRemainingTicks() - barched$f + 1.0F);
            SpearAnimations.firstPersonUse(((LivingEntityBridge) barched$abstractClientPlayer).getTicksSinceLastKineticHitFeedback(barched$f), barched$poseStack, l, humanoidArm, instance);
            this.barched$use = true;
            return UseAnim.NONE;
        }
        return instance.getUseAnimation();
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 12))
    private void barched$renderArmWithItemTranslate(PoseStack poseStack, float x, float y, float z, @Local(argsOnly = true) ItemStack itemStack) {
        SwingAnimationType type = ((ItemStackBridge) (Object) itemStack).getSwingAnimation().type();
        if (type != SwingAnimationType.STAB) {
            poseStack.translate(x, y, z);
        }
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 2))
    private void barched$applyItemArmTransform(ItemInHandRenderer instance, PoseStack poseStack, HumanoidArm humanoidArm, float f) {
        if (!this.barched$use) {
            this.applyItemArmTransform(poseStack, humanoidArm, f);
        } else {
            this.barched$use = false;
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

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    private float barched$tick(LocalPlayer instance, float v) {
        return ((PlayerBridge) instance).getItemSwapScale(v);
    }
}
