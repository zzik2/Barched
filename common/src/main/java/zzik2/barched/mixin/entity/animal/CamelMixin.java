package zzik2.barched.mixin.entity.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.camel.Camel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import zzik2.barched.bridge.entity.CamelBridge;

@Mixin(Camel.class)
public abstract class CamelMixin implements CamelBridge {

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", ordinal = 0), index = 2)
    private SoundEvent barched$tick(SoundEvent arg3) {
        return this.getDashReadySound();
    }

    @ModifyArg(method = "handleStartJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/camel/Camel;makeSound(Lnet/minecraft/sounds/SoundEvent;)V", ordinal = 0))
    private SoundEvent barched$makeDashingSound(SoundEvent par1) {
        return this.getDashingSound();
    }

    @ModifyArg(method = "standUp", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/camel/Camel;makeSound(Lnet/minecraft/sounds/SoundEvent;)V", ordinal = 0))
    private SoundEvent barched$makeStandUpSound(SoundEvent par1) {
        return this.getStandUpSound();
    }

    @ModifyArg(method = "sitDown", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/camel/Camel;makeSound(Lnet/minecraft/sounds/SoundEvent;)V", ordinal = 0))
    private SoundEvent barched$makeSitDownSound(SoundEvent par1) {
        return this.getSitDownSound();
    }

    @Override
    public SoundEvent getDashingSound() {
        return SoundEvents.CAMEL_DASH;
    }

    @Override
    public SoundEvent getDashReadySound() {
        return SoundEvents.CAMEL_DASH_READY;
    }

    @Override
    public SoundEvent getStandUpSound() {
        return SoundEvents.CAMEL_STAND;
    }

    @Override
    public SoundEvent getSitDownSound() {
        return SoundEvents.CAMEL_SIT;
    }
}
