package zzik2.barched.mixin.item.enchantment.effects;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.effects.ApplyEntityImpulse;
import net.minecraft.world.item.enchantment.effects.ApplyExhaustion;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentLocationBasedEffect.class)
public interface EnchantmentLocationBasedEffectMixin {

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void barched$bootstrap(Registry<MapCodec<? extends EnchantmentLocationBasedEffect>> registry, CallbackInfoReturnable<MapCodec<? extends EnchantmentLocationBasedEffect>> cir) {
        Registry.register(registry, (String)"apply_impulse", ApplyEntityImpulse.CODEC);
        Registry.register(registry, (String)"apply_exhaustion", ApplyExhaustion.CODEC);
    }
}
