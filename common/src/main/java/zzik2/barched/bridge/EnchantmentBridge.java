package zzik2.barched.bridge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;

public interface EnchantmentBridge {

    default void doLunge(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity) {
    }
}
