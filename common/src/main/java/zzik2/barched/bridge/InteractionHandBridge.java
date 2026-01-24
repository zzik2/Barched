package zzik2.barched.bridge;

import net.minecraft.world.entity.EquipmentSlot;

public interface InteractionHandBridge {

    default EquipmentSlot asEquipmentSlot() {
        return null;
    }
}
