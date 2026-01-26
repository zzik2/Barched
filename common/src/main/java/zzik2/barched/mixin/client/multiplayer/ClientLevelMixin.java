package zzik2.barched.mixin.client.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.level.LevelBridge;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements LevelBridge {

    @Shadow @Final private Minecraft minecraft;

    @Override
    public void playSeededSound(@Nullable Entity entity, double d, double e, double f, Holder<SoundEvent> holder, SoundSource soundSource, float g, float h, long l) {
        if (entity == this.minecraft.player) {
            this.playSound(d, e, f, holder.value(), soundSource, g, h, false, l);
        }
    }

    @Override
    public void playSeededSound(@Nullable Entity entity, Entity entity2, Holder<SoundEvent> holder, SoundSource soundSource, float f, float g, long l) {
        if (entity == this.minecraft.player) {
            this.minecraft.getSoundManager().play(new EntityBoundSoundInstance(holder.value(), soundSource, f, g, entity2, l));
        }

    }

    private void playSound(double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl, long l) {
        double i = this.minecraft.gameRenderer.getMainCamera().getPosition().distanceToSqr(d, e, f);
        SimpleSoundInstance simpleSoundInstance = new SimpleSoundInstance(soundEvent, soundSource, g, h, RandomSource.create(l), d, e, f);
        if (bl && i > 100.0D) {
            double j = Math.sqrt(i) / 40.0D;
            this.minecraft.getSoundManager().playDelayed(simpleSoundInstance, (int)(j * 20.0D));
        } else {
            this.minecraft.getSoundManager().play(simpleSoundInstance);
        }

    }
}
