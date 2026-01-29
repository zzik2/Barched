package zzik2.barched.mixin.entity.ai.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.entity.ai.LandRandomPosBridge;
import zzik2.zreflex.mixin.ModifyAccess;
import zzik2.zreflex.reflection.ZReflectionTool;

@Mixin(LandRandomPos.class)
public abstract class LandRandomPosMixin implements LandRandomPosBridge {

    @Shadow
    @Nullable
    private static Vec3 getPosInDirection(PathfinderMob arg, int i, int j, Vec3 arg2, boolean bl) {
        return null;
    }

    @Shadow
    @Nullable
    public static BlockPos movePosUpOutOfSolid(PathfinderMob arg, BlockPos arg2) {
        return null;
    }

    @Override
    public Vec3 getPosAway0(PathfinderMob pathfinderMob, double d, double e, int i, Vec3 vec3) {
        return getPosAway(pathfinderMob, d, e, i, vec3);
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Nullable
    private static Vec3 getPosAway(PathfinderMob pathfinderMob, double d, double e, int i, Vec3 vec3) {
        Vec3 vec32 = pathfinderMob.position().subtract(vec3);
        if (vec32.length() == 0.0D) {
            vec32 = new Vec3(pathfinderMob.getRandom().nextDouble() - 0.5D, 0.0D, pathfinderMob.getRandom().nextDouble() - 0.5D);
        }

        boolean bl = ZReflectionTool.invokeMethod(GoalUtils.class, "mobRestricted", pathfinderMob, e);
        return getPosInDirection(pathfinderMob, d, e, i, vec32, bl);
    }

    @Nullable
    private static Vec3 getPosInDirection(PathfinderMob pathfinderMob, double d, double e, int i, Vec3 vec3, boolean bl) {
        return RandomPos.generateRandomPos(pathfinderMob, () -> {
            BlockPos blockPos = ZReflectionTool.invokeMethod(RandomPos.class, "generateRandomDirectionWithinRadians", pathfinderMob.getRandom(), d, e, i, 0, vec3.x, vec3.z, 1.5707963705062866D);
            if (blockPos == null) {
                return null;
            } else {
                BlockPos blockPos2 = generateRandomPosTowardDirection(pathfinderMob, e, bl, blockPos);
                return blockPos2 == null ? null : movePosUpOutOfSolid(pathfinderMob, blockPos2);
            }
        });
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Nullable
    private static BlockPos generateRandomPosTowardDirection(PathfinderMob pathfinderMob, double d, boolean bl, BlockPos blockPos) {
        BlockPos blockPos2 = ZReflectionTool.invokeMethod(RandomPos.class, "generateRandomPosTowardDirection", pathfinderMob, d, pathfinderMob.getRandom(), blockPos);
        return !GoalUtils.isOutsideLimits(blockPos2, pathfinderMob) && !GoalUtils.isRestricted(bl, pathfinderMob, blockPos2) && !GoalUtils.isNotStable(pathfinderMob.getNavigation(), blockPos2) ? blockPos2 : null;
    }
}
