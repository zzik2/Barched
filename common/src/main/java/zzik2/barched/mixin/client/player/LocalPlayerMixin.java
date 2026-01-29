package zzik2.barched.mixin.client.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.component.UseEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements LivingEntityBridge {

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Shadow protected abstract boolean isControlledCamera();

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract boolean isMovingSlowly();

    @Shadow public Input input;

    @Shadow public float yBobO;

    @Shadow public float yBob;

    @Shadow public float xBob;

    @Shadow public float xBobO;

    @Shadow protected int sprintTriggerTime;

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;canStartSprinting()Z"))
    private boolean barched$aiStep(boolean original) {
        return original && !this.isSlowDueToUsingItem();
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean barched$aiStep0(boolean original) {
        return original && this.isSlowDueToUsingItem();
    }

    @ModifyExpressionValue(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean barched$canStartSprinting(boolean original) {
        return original && this.isSlowDueToUsingItem();
    }

    @Unique
    private boolean isSlowDueToUsingItem() {
        return this.isUsingItem() && !((UseEffects)this.useItem.getOrDefault(Barched.DataComponents.USE_EFFECTS, UseEffects.DEFAULT)).canSprint();
    }

    @Unique
    private float itemUseSpeedMultiplier() {
        return ((UseEffects)this.useItem.getOrDefault(Barched.DataComponents.USE_EFFECTS, UseEffects.DEFAULT)).speedMultiplier();
    }
}
