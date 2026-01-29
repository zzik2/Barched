package zzik2.barched.bridge;

import net.minecraft.world.phys.Vec3;

public interface Vec3Bridge {

    default Vec3 addLocalCoordinates(Vec3 vec3) {
        return null;
    }
}
