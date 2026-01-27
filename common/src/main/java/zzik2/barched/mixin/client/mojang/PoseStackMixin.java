package zzik2.barched.mixin.client.mojang;

import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.BarchedClient;

@Mixin(value = PoseStack.class, remap = false)
public class PoseStackMixin {

    @Inject(method = "translate(FFF)V", at = @At("HEAD"), cancellable = true)
    private void barched$translate(float f, float g, float h, CallbackInfo ci) {
        if (BarchedClient.ItemInHandRenderer.barched$isStab.getAndSet(false)) {
            ci.cancel();
        }
    }

    @Inject(method = "translate(DDD)V", at = @At("HEAD"), cancellable = true)
    private void barched$translate(double d, double e, double f, CallbackInfo ci) {
        if (BarchedClient.ItemInHandRenderer.barched$isStab.getAndSet(false)) {
            ci.cancel();
        }
    }
}
