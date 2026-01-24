package zzik2.barched.bridge.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface PlayerBridge extends LivingEntityBridge {

    default void applyPostImpulseGraceTime(int i) {}

    default boolean hasEnoughFoodToDoExhaustiveManoeuvres() {
        return false;
    }

    default float getItemSwapScale(float f) {
        return 0.0F;
    }

    default void resetOnlyAttackStrengthTicker() {
    }

    default boolean cannotAttackWithItem(ItemStack itemStack, int i) {
        return false;
    }

    default boolean cannotAttack(Entity entity) {
        return false;
    }

    default boolean deflectProjectile(Entity entity) {
        return false;
    }

    default float baseDamageScaleFactor() {
        return 0.0F;
    }

    default void damageStatsAndHearts(Entity entity, float f) {
    }

    default void attackVisualEffects(Entity entity, boolean bl, boolean bl2, boolean bl3, boolean bl4, float f) {
    }

    default void itemAttackInteraction(Entity entity, ItemStack itemStack, DamageSource damageSource, boolean bl) {
    }

    default void playServerSideSound(SoundEvent soundEvent) {
    }
}
