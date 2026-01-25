package zzik2.barched.mixin.client.renderer;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.item.ItemStackBridge;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;", ordinal = 0, shift = At.Shift.BEFORE))
    private void barched$renderArmWithItem(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci) {
        boolean bl = interactionHand == InteractionHand.MAIN_HAND;
        HumanoidArm humanoidArm = bl ? abstractClientPlayer.getMainArm() : abstractClientPlayer.getMainArm().getOpposite();
        boolean bl2 = humanoidArm == HumanoidArm.RIGHT;
        int q = bl2 ? 1 : -1;

        if (itemStack.getUseAnimation() == Barched.UseAnim.BARCHED$SPEAR) {
            poseStack.translate((float)q * 0.56F, -0.52F, -0.72F);
            float l = (float)itemStack.getUseDuration(abstractClientPlayer) - ((float)abstractClientPlayer.getUseItemRemainingTicks() - f + 1.0F);
            SpearAnimations.firstPersonUse(((LivingEntityBridge) abstractClientPlayer).getTicksSinceLastKineticHitFeedback(f), poseStack, l, humanoidArm, itemStack);
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 12, shift = At.Shift.BEFORE), cancellable = true)
    private void barched$renderArmWithItem0(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci) {
        if (((ItemStackBridge) (Object) itemStack).getSwingAnimation().type() == SwingAnimationType.STAB) {
            boolean bl = interactionHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidArm = bl ? abstractClientPlayer.getMainArm() : abstractClientPlayer.getMainArm().getOpposite();
            boolean bl2 = humanoidArm == HumanoidArm.RIGHT;
            int q = bl2 ? 1 : -1;
            SpearAnimations.firstPersonAttack(h, poseStack, q, humanoidArm);
            ci.cancel();
        }
    }

}
