package zzik2.barched.mixin.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.function.Function;

@Mixin(ExtraCodecs.class)
public abstract class ExtraCodecsMixin {

    @Shadow
    private static Codec<Float> floatRangeMinExclusiveWithMessage(float f, float g, Function<Float, String> function) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Codec<Float> floatRange(float f, float g) {
        return floatRangeMinExclusiveWithMessage(f, g, (float_) -> {
            return "Value must be within range [" + f + ";" + g + "]: " + float_;
        });
    }
}
