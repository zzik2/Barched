package zzik2.barched.mixin.entity;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zzik2.barched.Barched;
import zzik2.zreflex.reflection.ZReflectionTool;


@Mixin(SpawnPlacements.class)
public abstract class SpawnPlacementsMixin {

    @Shadow
    @Deprecated
    private static <T extends Mob> void register(EntityType<T> arg, SpawnPlacementType arg2, Heightmap.Types arg3, SpawnPlacements.SpawnPredicate<T> arg4) {
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/SpawnPlacements;register(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/SpawnPlacementType;Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/world/entity/SpawnPlacements$SpawnPredicate;)V"))
    private static <T extends Mob> void barched$redirectCheckRule(EntityType<T> spawnplacements$data, SpawnPlacementType arg, Heightmap.Types arg2, SpawnPlacements.SpawnPredicate<T> arg3) {
        if (spawnplacements$data == EntityType.ZOMBIE_HORSE) {
            register(spawnplacements$data, arg, arg2, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> {
                return ZReflectionTool.invokeStaticMethod(Monster.class, "checkMonsterSpawnRules0", entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
            }));
            return;
        }
        register(spawnplacements$data, arg, arg2, arg3);
    }

    static {
        register(Barched.EntityType.PARCHED, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> {
            return ZReflectionTool.invokeStaticMethod(Monster.class, "checkSurfaceMonstersSpawnRules", entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
        }));
        register(Barched.EntityType.CAMEL_HUSK, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> {
            return ZReflectionTool.invokeStaticMethod(Monster.class, "checkSurfaceMonstersSpawnRules", entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
        }));
    }
}
