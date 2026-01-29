package zzik2.barched.bridge.entity;

import net.minecraft.world.entity.EquipmentSlot;

public interface MobBridge extends LivingEntityBridge {

    default EquipmentSlot sunProtectionSlot() {
        return null;
    }

    default void burnUndead() {
    }

    default float chargeSpeedModifier() {
        return 0.0F;
    }
}
