package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.BarchedClient;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.item.ItemStackBridge;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Shadow public HumanoidModel.ArmPose rightArmPose;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart head;

    @Shadow public HumanoidModel.ArmPose leftArmPose;

    @Shadow @Final public ModelPart leftArm;

    @Inject(method = "setupAttackAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;sin(F)F", ordinal = 4, shift = At.Shift.AFTER), cancellable = true)
    private void barched$setupAttackAnimation(T livingEntity, float f, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getMainHandItem();
        if (((ItemStackBridge) (Object) itemStack).getSwingAnimation().type() == SwingAnimationType.STAB) {
            SpearAnimations.thirdPersonAttackHand((HumanoidModel) (Object) this, livingEntity);
            ci.cancel();
        }
    }

    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    private void barched$poseRightArm(T livingEntity, CallbackInfo ci) {
        if (this.rightArmPose == BarchedClient.ArmPose.SPEAR) {
            SpearAnimations.thirdPersonHandUse(this.rightArm, this.head, true, ((LivingEntityBridge) livingEntity).barched$getUseItemStackForArm(HumanoidArm.RIGHT), livingEntity);
            ci.cancel();
        }
    }

    @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
    private void barched$poseLeftArm(T livingEntity, CallbackInfo ci) {
        if (this.leftArmPose == BarchedClient.ArmPose.SPEAR) {
            SpearAnimations.thirdPersonHandUse(this.leftArm, this.head, false, ((LivingEntityBridge) livingEntity).barched$getUseItemStackForArm(HumanoidArm.LEFT), livingEntity);
            ci.cancel();
        }
    }
}
