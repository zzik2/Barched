package zzik2.barched.mixin.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zzik2.barched.bridge.client.LocalPlayerBridge;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow @Final Minecraft minecraft;

    @Redirect(method = "pick(F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;"))
    private HitResult barched$pick(GameRenderer instance, Entity entity, double d, double e, float f) {
        return ((LocalPlayerBridge) this.minecraft.player).raycastHitResult(f, entity);
    }
}
