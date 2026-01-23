package zzik2.barched.bridge;

import net.minecraft.sounds.SoundEvent;

public interface CamelBridge {

    default SoundEvent getDashingSound() {
        return null;
    }

    default SoundEvent getDashReadySound() {
        return null;
    }

    default SoundEvent getStandUpSound() {
        return null;
    }

    default SoundEvent getSitDownSound() {
        return null;
    }
}
