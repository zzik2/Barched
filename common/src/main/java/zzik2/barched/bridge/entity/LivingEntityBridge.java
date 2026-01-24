package zzik2.barched.bridge.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.phys.Vec3;

public interface LivingEntityBridge {

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
}
