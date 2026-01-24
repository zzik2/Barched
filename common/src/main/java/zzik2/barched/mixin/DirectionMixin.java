package zzik2.barched.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;

import static net.minecraft.core.Direction.NORTH;

@Mixin(Direction.class)
public class DirectionMixin {

    @Shadow @Final private static Direction[] VALUES;

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Direction getApproximateNearest(double d, double e, double f) {
        return getApproximateNearest((float)d, (float)e, (float)f);
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Direction getApproximateNearest(float f, float g, float h) {
        Direction direction = NORTH;
        float i = Float.MIN_VALUE;
        Direction[] var5 = VALUES;
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Direction direction2 = var5[var7];
            float j = f * (float)direction2.getNormal().getX() + g * (float)direction2.getNormal().getY() + h * (float)direction2.getNormal().getZ();
            if (j > i) {
                i = j;
                direction = direction2;
            }
        }

        return direction;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Direction getApproximateNearest(Vec3 vec3) {
        return getApproximateNearest(vec3.x, vec3.y, vec3.z);
    }
}
