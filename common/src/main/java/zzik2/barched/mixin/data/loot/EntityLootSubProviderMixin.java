package zzik2.barched.mixin.data.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.EntityLootSubProviderBridge;

import java.util.Map;

@Mixin(EntityLootSubProvider.class)
public class EntityLootSubProviderMixin implements EntityLootSubProviderBridge {

    @Shadow @Final private Map<EntityType<?>, Map<ResourceKey<LootTable>, LootTable.Builder>> map;

    @Override
    public void barched$remove(EntityType<?> entityType) {
        this.map.remove(entityType);
    }
}
