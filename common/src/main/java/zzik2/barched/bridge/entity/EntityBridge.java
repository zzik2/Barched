package zzik2.barched.bridge.entity;

import net.minecraft.world.phys.Vec3;

public interface EntityBridge {

    default Vec3 getHeadLookAngle() {
        return null;
    }

    default Vec3 getKnownSpeed() {
        return null;
    }

    default void computeSpeed() {
    }
}
