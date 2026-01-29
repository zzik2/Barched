package zzik2.barched.bridge.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

public interface RandomPosBridge {

    default BlockPos generateRandomDirectionWithinRadians0(RandomSource randomSource, double d, double e, int i, int j, double f, double g, double h) {
        return null;
    }
}
