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

    /**
     * Server, Client
     * @param entity
     * @param d
     * @param e
     * @param f
     * @param holder
     * @param soundSource
     * @param g
     * @param h
     * @param l
     */
    default void playSeededSound(@Nullable Entity entity, double d, double e, double f, Holder<SoundEvent> holder, SoundSource soundSource, float g, float h, long l) {
    }

    /**
     * Level.class Only
     * @param entity
     * @param d
     * @param e
     * @param f
     * @param soundEvent
     * @param soundSource
     * @param g
     * @param h
     * @param l
     */
    default void playSeededSound(@Nullable Entity entity, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, long l) {
    }

    /**
     * Server, Client
     * @param entity
     * @param entity2
     * @param holder
     * @param soundSource
     * @param f
     * @param g
     * @param l
     */
    default void playSeededSound(@Nullable Entity entity, Entity entity2, Holder<SoundEvent> holder, SoundSource soundSource, float f, float g, long l) {
    }

}
