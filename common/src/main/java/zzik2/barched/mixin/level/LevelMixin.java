package zzik2.barched.mixin.level;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.level.LevelBridge;

@Mixin(Level.class)
public abstract class LevelMixin implements LevelBridge {

    @Shadow @Final @Deprecated private RandomSource threadSafeRandom;

    @Override
    public void playSound(@Nullable Entity entity, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h) {
        this.playSeededSound(entity, d, e, f, soundEvent, soundSource, g, h, this.threadSafeRandom.nextLong());
    }

    @Override
    public void playSound(@Nullable Entity entity, double d, double e, double f, Holder<SoundEvent> holder, SoundSource soundSource, float g, float h) {
        this.playSeededSound(entity, d, e, f, holder, soundSource, g, h, this.threadSafeRandom.nextLong());
    }

    @Override
    public void playSeededSound(@Nullable Entity entity, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, long l) {
        this.playSeededSound(entity, d, e, f, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent), soundSource, g, h, l);
    }
}
