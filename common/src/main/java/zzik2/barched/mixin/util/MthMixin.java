package zzik2.barched.mixin.util;

import net.minecraft.util.Mth;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(Mth.class)
public class MthMixin {

    @Shadow @Final private static float[] SIN;

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static float cos(double d) {
        return SIN[(int)((long)(d * 10430.378350470453D + 16384.0D) & 65535L)];
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static float sin(double d) {
        return SIN[(int)((long)(d * 10430.378350470453D) & 65535L)];
    }
}
