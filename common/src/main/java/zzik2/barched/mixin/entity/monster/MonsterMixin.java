package zzik2.barched.mixin.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import zzik2.zreflex.mixin.ModifyAccess;


import static net.minecraft.world.entity.Mob.checkMobSpawnRules;

@Mixin(Monster.class)
public abstract class MonsterMixin {

    @Shadow
    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor arg, BlockPos arg2, RandomSource arg3) {
        return false;
    }

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static boolean checkMonsterSpawnRules0(EntityType<? extends Mob> arg, ServerLevelAccessor arg2, MobSpawnType arg3, BlockPos arg4, RandomSource arg5) {
        return arg2.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(arg3) || isDarkEnoughToSpawn(arg2, arg4, arg5)) && checkMobSpawnRules(arg, arg2, arg3, arg4, arg5);
    }

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static boolean checkSurfaceMonstersSpawnRules(EntityType<? extends Mob> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
        return checkMonsterSpawnRules(entityType, serverLevelAccessor, entitySpawnReason, blockPos, randomSource) && (MobSpawnType.isSpawner(entitySpawnReason) || serverLevelAccessor.canSeeSky(blockPos));
    }

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static boolean checkMonsterSpawnRules(EntityType<? extends Mob> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
        return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(entitySpawnReason) || isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource)) && checkMobSpawnRules(entityType, serverLevelAccessor, entitySpawnReason, blockPos, randomSource);
    }
}
