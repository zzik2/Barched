package zzik2.barched.mixin.client.renderer.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;
import zzik2.barched.BarchedClient;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "getArmPose", at = @At("RETURN"), cancellable = true)
    private static void barched$getArmPose(AbstractClientPlayer abstractClientPlayer, InteractionHand interactionHand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        ItemStack itemStack = abstractClientPlayer.getItemInHand(interactionHand);
        if (abstractClientPlayer.getUsedItemHand() == interactionHand && abstractClientPlayer.getUseItemRemainingTicks() > 0) {
            UseAnim useAnim = itemStack.getUseAnimation();
            if (useAnim == Barched.UseAnim.BARCHED$SPEAR) {
                cir.setReturnValue(BarchedClient.ArmPose.SPEAR);
                return;
            }
        }

        SwingAnimation swingAnimation = (SwingAnimation)itemStack.get(Barched.DataComponents.SWING_ANIMATION);
        if (swingAnimation != null && swingAnimation.type() == SwingAnimationType.STAB && abstractClientPlayer.swinging) {
            cir.setReturnValue(BarchedClient.ArmPose.SPEAR);
        } else {
            cir.setReturnValue(itemStack.is(Barched.ItemTags.SPEARS) ? BarchedClient.ArmPose.SPEAR : HumanoidModel.ArmPose.ITEM);
        }
    }
}
