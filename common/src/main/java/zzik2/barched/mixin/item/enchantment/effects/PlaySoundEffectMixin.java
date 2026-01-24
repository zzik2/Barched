package zzik2.barched.mixin.item.enchantment.effects;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.level.LevelBridge;

import java.util.List;

@Mixin(PlaySoundEffect.class)
public abstract class PlaySoundEffectMixin implements EnchantmentEntityEffect {

    @Shadow @Final private Holder<SoundEvent> soundEvent;
    @Shadow @Final private FloatProvider volume;
    @Shadow @Final private FloatProvider pitch;
    private static final List<Holder<SoundEvent>> SPEAR_SOUDNS = List.of(Barched.SoundEvents.LUNGE_1, Barched.SoundEvents.LUNGE_2, Barched.SoundEvents.LUNGE_3);

    @Inject(method = "apply", at =  @At("HEAD"), cancellable = true)
    private void barched$apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3, CallbackInfo ci) {
        if (this.soundEvent == Barched.SoundEvents.LUNGE_1) {
            RandomSource randomSource = entity.getRandom();
            if (!entity.isSilent()) {
                int j = Mth.clamp(i - 1, 0, SPEAR_SOUDNS.size() - 1);
                ((LevelBridge) serverLevel).playSound((Entity)null, vec3.x(), vec3.y(), vec3.z(), SPEAR_SOUDNS.get(j), entity.getSoundSource(), this.volume.sample(randomSource), this.pitch.sample(randomSource));
                ci.cancel();
            }
        }
    }
}
