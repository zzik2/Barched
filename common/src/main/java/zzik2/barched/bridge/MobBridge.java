package zzik2.barched.bridge;

import net.minecraft.world.entity.EquipmentSlot;

public interface MobBridge {

    default EquipmentSlot sunProtectionSlot() {
        return null;
    }

    default void burnUndead() {
    }
}
