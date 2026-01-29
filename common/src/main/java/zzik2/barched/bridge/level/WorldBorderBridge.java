package zzik2.barched.bridge.level;

import net.minecraft.world.phys.Vec3;

public interface WorldBorderBridge {

    default Vec3 clampVec3ToBound(Vec3 vec3) {
        return null;
    }

    default Vec3 clampVec3ToBound(double d, double e, double f) {
        return null;
    }
}
