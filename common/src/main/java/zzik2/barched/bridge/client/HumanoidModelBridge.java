package zzik2.barched.bridge.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface HumanoidModelBridge<T extends LivingEntity> {

    default HumanoidModel.ArmPose super$getArmPose(T livingEntity, HumanoidArm humanoidArm, @Nullable HumanoidModel.ArmPose fallback) {
        throw new RuntimeException("not implemented!");
    }

    default HumanoidModel.ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm, @Nullable HumanoidModel.ArmPose fallback) {
        throw new RuntimeException("not implemented!");
    }

    @Nullable
    default HumanoidModel.ArmPose getArmPoseFallback() {
        throw new RuntimeException("not implemented!");
    }

    // AbstractZombieModel
    default HumanoidModel.ArmPose abstractZombie$super$getArmPose(T livingEntity, HumanoidArm humanoidArm, @Nullable HumanoidModel.ArmPose fallback) {
        throw new RuntimeException("not implemented!");
    }
}
