package zzik2.barched.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.client.HumanoidModel$ArmPoseBridge;

@Mixin(HumanoidModel.ArmPose.class)
public abstract class HumanoidModel$ArmPoseMixin implements HumanoidModel$ArmPoseBridge {
}
