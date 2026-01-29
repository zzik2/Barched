package zzik2.barched.mixin.damagesource;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.DamageSourcesBridge;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements DamageSourcesBridge {

    @Shadow protected abstract DamageSource source(ResourceKey<DamageType> arg, Entity arg2);

    @Override
    public DamageSource mace(Entity entity) {
        return this.source(Barched.DamageTypes.MACE_SMASH, entity);
    }
}
