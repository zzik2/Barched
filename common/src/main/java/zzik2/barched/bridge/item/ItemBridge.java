package zzik2.barched.bridge.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface ItemBridge {

    @Deprecated
    @Nullable
    default DamageSource getItemDamageSource(LivingEntity livingEntity) {
        return null;
    }
    @Nullable
    default DamageSource super$getItemDamageSource(LivingEntity livingEntity) {
        return null;
    }
}
