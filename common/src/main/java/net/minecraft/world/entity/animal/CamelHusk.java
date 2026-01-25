package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.CamelBridge;
import zzik2.zreflex.mixin.ModifyName;

public class CamelHusk extends Camel implements CamelBridge {

    public CamelHusk(EntityType<? extends Camel> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return true;
    }

    @Override
    public boolean isMobControlled() {
        return this.getFirstPassenger() instanceof Mob;
    }


    @ModifyName(value = "interact")
    public InteractionResult interact0(Player player, InteractionHand interactionHand) {
        this.setPersistenceRequired();
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public boolean canBeLeashed() {
        return !this.isMobControlled();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Barched.ItemTags.CAMEL_HUSK_FOOD);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Barched.SoundEvents.CAMEL_HUSK_AMBIENT;
    }

    @Override
    public boolean canMate(Animal animal) {
        return false;
    }

    @Override
    public @Nullable Camel getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Barched.SoundEvents.CAMEL_HUSK_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return Barched.SoundEvents.CAMEL_HUSK_HURT;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        if (blockState.is(BlockTags.CAMEL_SAND_STEP_SOUND_BLOCKS)) {
            this.playSound(Barched.SoundEvents.CAMEL_HUSK_STEP_SAND, 0.4F, 1.0F);
        } else {
            this.playSound(Barched.SoundEvents.CAMEL_HUSK_STEP, 0.4F, 1.0F);
        }
    }

    @Override
    public SoundEvent getDashingSound() {
        return Barched.SoundEvents.CAMEL_HUSK_DASH;
    }

    @Override
    public SoundEvent getDashReadySound() {
        return Barched.SoundEvents.CAMEL_HUSK_DASH_READY;
    }

    @Override
    protected SoundEvent getEatingSound() {
        return Barched.SoundEvents.CAMEL_HUSK_EAT;
    }

    @Override
    public SoundEvent getStandUpSound() {
        return Barched.SoundEvents.CAMEL_HUSK_STAND;
    }

    @Override
    public SoundEvent getSitDownSound() {
        return Barched.SoundEvents.CAMEL_HUSK_SIT;
    }

    @Override
    public @NotNull SoundEvent getSaddleSoundEvent() {
        return Barched.SoundEvents.CAMEL_HUSK_SADDLE;
    }

    // TODO
    @Override
    public float chargeSpeedModifier() {
        return 4.0F;
    }
}