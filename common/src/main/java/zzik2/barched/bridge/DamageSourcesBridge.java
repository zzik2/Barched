package zzik2.barched.bridge;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public interface DamageSourcesBridge {

    default DamageSource mace(Entity entity) {
        return null;
    }
}
