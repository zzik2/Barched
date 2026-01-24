package zzik2.barched.bridge.entity;

import net.minecraft.world.phys.Vec3;

public interface EntityBridge extends LivingEntityBridge {

    default void barched$snapTo(double d, double e, double f, float g, float h) {}

    default Vec3 getHeadLookAngle() {
        return null;
    }
}
