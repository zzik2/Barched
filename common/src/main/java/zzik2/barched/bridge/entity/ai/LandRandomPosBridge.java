package zzik2.barched.bridge.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public interface LandRandomPosBridge {

    default Vec3 getPosAway0(PathfinderMob pathfinderMob, double d, double e, int i, Vec3 vec3) {
        return null;
    }
}
