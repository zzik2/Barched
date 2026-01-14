package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import zzik2.barched.Barched;

public class CamelHusk extends Camel {

    public CamelHusk(EntityType<? extends Camel> entityType, Level level) {
        super(entityType, level);
    }

    public boolean removeWhenFarAway(double d) {
        return true;
    }

    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Barched.ItemTags.CAMEL_HUSK_FOOD);
    }

    protected SoundEvent getAmbientSound() {
        return Barched.SoundEvents.CAMEL_HUSK_AMBIENT;
    }

    public boolean canMate(Animal animal) {
        return false;
    }

    public @Nullable Camel getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public boolean canFallInLove() {
        return false;
    }

    protected SoundEvent getDeathSound() {
        return Barched.SoundEvents.CAMEL_HUSK_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return Barched.SoundEvents.CAMEL_HUSK_HURT;
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        if (blockState.is(BlockTags.CAMEL_SAND_STEP_SOUND_BLOCKS)) {
            this.playSound(Barched.SoundEvents.CAMEL_HUSK_STEP_SAND, 0.4F, 1.0F);
        } else {
            this.playSound(Barched.SoundEvents.CAMEL_HUSK_STEP, 0.4F, 1.0F);
        }

    }

    protected SoundEvent getDashingSound() {
        return Barched.SoundEvents.CAMEL_HUSK_DASH;
    }

    protected SoundEvent getDashReadySound() {
        return Barched.SoundEvents.CAMEL_HUSK_DASH_READY;
    }

    protected SoundEvent getEatingSound() {
        return Barched.SoundEvents.CAMEL_HUSK_EAT;
    }

    protected SoundEvent getStandUpSound() {
        return Barched.SoundEvents.CAMEL_HUSK_STAND;
    }

    protected SoundEvent getSitDownSound() {
        return Barched.SoundEvents.CAMEL_HUSK_SIT;
    }

    protected Holder.Reference<SoundEvent> getSaddleSound() {
        return Barched.SoundEvents.CAMEL_HUSK_SADDLE;
    }

    public float chargeSpeedModifier() {
        return 4.0F;
    }
}