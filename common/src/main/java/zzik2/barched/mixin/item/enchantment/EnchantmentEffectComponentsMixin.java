package zzik2.barched.mixin.item.enchantment;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.function.UnaryOperator;

@Mixin(EnchantmentEffectComponents.class)
public interface EnchantmentEffectComponentsMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Shadow
    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return null;
    }
}
