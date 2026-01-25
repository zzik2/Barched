package zzik2.barched.bridge.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public interface AnimationUtilsBridge {

    default <T extends LivingEntity> void barched$animateZombieArms0(ModelPart modelPart, ModelPart modelPart2, boolean bl, float f, float g, T livingEntity) {
    }
}
