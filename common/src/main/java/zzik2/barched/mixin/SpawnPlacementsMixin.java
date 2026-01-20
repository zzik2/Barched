package zzik2.barched.mixin;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.zreflex.reflection.ZReflectionTool;


@Mixin(SpawnPlacements.class)
public abstract class SpawnPlacementsMixin {

    @Shadow
    @Deprecated
    private static <T extends Mob> void register(EntityType<T> arg, SpawnPlacementType arg2, Heightmap.Types arg3, SpawnPlacements.SpawnPredicate<T> arg4) {
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
