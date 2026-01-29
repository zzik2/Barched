package zzik2.barched.mixin.entity.ai.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.RandomPos;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import zzik2.barched.bridge.entity.ai.RandomPosBridge;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(RandomPos.class)
public abstract class RandomPosMixin implements RandomPosBridge {

    @Override
    public BlockPos generateRandomDirectionWithinRadians0(RandomSource randomSource, double d, double e, int i, int j, double f, double g, double h) {
        return generateRandomDirectionWithinRadians(randomSource, d, e, i, j, f, g, h);
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Nullable
    @Unique
    private static BlockPos generateRandomDirectionWithinRadians(RandomSource randomSource, double d, double e, int i, int j, double f, double g, double h) {
        double k = Mth.atan2(g, f) - 1.5707963705062866D;
        double l = k + (double)(2.0F * randomSource.nextFloat() - 1.0F) * h;
        double m = Mth.lerp(Math.sqrt(randomSource.nextDouble()), d, e) * (double)Mth.SQRT_OF_TWO;
        double n = -m * Math.sin(l);
        double o = m * Math.cos(l);
        if (!(Math.abs(n) > e) && !(Math.abs(o) > e)) {
            int p = randomSource.nextInt(2 * i + 1) - i + j;
            return BlockPos.containing(n, (double)p, o);
        } else {
            return null;
        }
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Unique
    private static BlockPos generateRandomPosTowardDirection(PathfinderMob pathfinderMob, double d, RandomSource randomSource, BlockPos blockPos) {
        double e = (double)blockPos.getX();
        double f = (double)blockPos.getZ();
        if (pathfinderMob.hasRestriction() && d > 1.0D) {
            BlockPos blockPos2 = pathfinderMob.getRestrictCenter();
            if (pathfinderMob.getX() > (double)blockPos2.getX()) {
                e -= randomSource.nextDouble() * d / 2.0D;
            } else {
                e += randomSource.nextDouble() * d / 2.0D;
            }

            if (pathfinderMob.getZ() > (double)blockPos2.getZ()) {
                f -= randomSource.nextDouble() * d / 2.0D;
            } else {
                f += randomSource.nextDouble() * d / 2.0D;
            }
        }

        return BlockPos.containing(e + pathfinderMob.getX(), (double)blockPos.getY() + pathfinderMob.getY(), f + pathfinderMob.getZ());
    }
}
