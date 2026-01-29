package zzik2.barched.mixin.item.enchantment;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.function.UnaryOperator;

@Mixin(EnchantmentEffectComponents.class)
public interface EnchantmentEffectComponentsMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Shadow
    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return null;
    }

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void barched$bootstrap(Registry<DataComponentType<?>> registry, CallbackInfoReturnable<DataComponentType<?>> cir) {
        Barched.EnchantmentEffectComponents.POST_PIERCING_ATTACK = register("post_piercing_attack", (builder) -> {
            return builder.persistent(ConditionalEffect.codec(EnchantmentEntityEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf());
        });
    }
}
