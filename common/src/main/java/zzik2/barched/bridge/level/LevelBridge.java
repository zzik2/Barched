package zzik2.barched.bridge.level;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface LevelBridge extends CollisionGetterBridge {

    default void playSound(@Nullable Entity entity, double d, double e, double f, Holder<SoundEvent> holder, SoundSource soundSource, float g, float h) {
    }

    default void playSound(@Nullable Entity entity, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h) {
    }

    default void playSeededSound(@Nullable Entity entity, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, long l) {
    }
}
