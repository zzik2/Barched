package zzik2.barched.bridge.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public interface HumanoidMobRendererBridge<T extends LivingEntity> {

    default HumanoidModel.ArmPose super$getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        throw new RuntimeException("not implemented!");
    }

    default HumanoidModel.ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        throw new RuntimeException("not implemented!");
    }
}
