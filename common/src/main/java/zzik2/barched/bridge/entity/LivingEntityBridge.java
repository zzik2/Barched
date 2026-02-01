package zzik2.barched.bridge.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public interface LivingEntityBridge extends EntityBridge {

    default void super$lungeForwardMaybe() {}
    default void lungeForwardMaybe() {}

    default void super$onAttack() {}
    default void onAttack() {}

    default AttackRange entityAttackRange() {
        return null;
    }

    default ItemStack getActiveItem() {
        return null;
    }

    default boolean super$stabAttack(EquipmentSlot equipmentSlot, Entity entity, float f, boolean bl, boolean bl2, boolean bl3) {
        return false;
    }

    default boolean stabAttack(EquipmentSlot equipmentSlot, Entity entity, float f, boolean bl, boolean bl2, boolean bl3) {
        return false;
    }

    default void causeExtraKnockback(Entity entity, float f, Vec3 vec3) {
    }

    default boolean wasRecentlyStabbed(Entity entity, int i) {
        return false;
    }

    default void rememberStabbedEntity(Entity entity) {
    }

    default int stabbedEntities(Predicate<Entity> predicate) {
        return 0;
    }

    default float getTicksSinceLastKineticHitFeedback(float f) {
        return 0.0F;
    }

    default ItemStack getItemHeldByArm(HumanoidArm humanoidArm) {
        return null;
    }

    default InteractionHand getUsedItemHand() {
        return null;
    }
    
    default float getTicksUsingItem(float f) {
        return 0.0F;
    }

    //ArmedEntityRenderState-Like
    default ItemStack barched$getMainHandItemStack() {
        return null;
    }

    default ItemStack barched$getUseItemStackForArm(HumanoidArm humanoidArm) {
        return null;
    }

    default float barched$ticksUsingItem(HumanoidArm humanoidArm, float partialTick) {
        return 0.0F;
    }
}
