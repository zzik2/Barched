package zzik2.barched.bridge.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SwingAnimation;

import java.util.function.Supplier;

public interface ItemStackBridge {

    default SwingAnimation getSwingAnimation() {
        return null;
    }

    default DamageSource getDamageSource(LivingEntity livingEntity, Supplier<DamageSource> supplier) {
        return null;
    }
}
