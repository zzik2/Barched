package zzik2.barched.mixin.accessor.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HumanoidModel.class)
public interface HumanoidModelAccessor {

    @Invoker("getArm")
    ModelPart getGetArm(HumanoidArm humanoidArm);

}
