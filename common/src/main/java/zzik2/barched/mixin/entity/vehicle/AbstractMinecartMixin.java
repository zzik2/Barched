package zzik2.barched.mixin.entity.vehicle;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.bridge.entity.EntityBridge;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartMixin implements EntityBridge {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/AbstractMinecart;checkBelowWorld()V", shift = At.Shift.AFTER))
    private void barched$tick(CallbackInfo ci) {
        this.computeSpeed();
    }
}
