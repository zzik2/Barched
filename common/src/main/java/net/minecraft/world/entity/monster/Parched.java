package net.minecraft.world.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.EntityBridge;

public class Parched extends AbstractSkeleton implements EntityBridge {

    public Parched(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull AbstractArrow getArrow(ItemStack itemStack, float f, @Nullable ItemStack itemStack2) {
        AbstractArrow abstractArrow = super.getArrow(itemStack, f, itemStack2);
        if (abstractArrow instanceof Arrow) {
            ((Arrow)abstractArrow).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600));
        }

        return abstractArrow;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return AbstractSkeleton.createAttributes().add(Attributes.MAX_HEALTH, (double)16.0F);
    }

    @Override
    public SoundEvent getStepSound() {
        return Barched.SoundEvents.PARCHED_STEP;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return Barched.SoundEvents.PARCHED_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(DamageSource damageSource) {
        return Barched.SoundEvents.PARCHED_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return Barched.SoundEvents.PARCHED_DEATH;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected int getHardAttackInterval() {
        return 50;
    }

    @Override
    protected int getAttackInterval() {
        return 70;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
        return mobEffectInstance.getEffect() == MobEffects.WEAKNESS ? false : super.canBeAffected(mobEffectInstance);
    }
}