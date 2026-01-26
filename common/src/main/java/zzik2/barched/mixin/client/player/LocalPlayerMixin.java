package zzik2.barched.mixin.client.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.UseEffects;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;canStartSprinting()Z"))
    private boolean barched$aiStep(boolean original) {
        return original && !this.isSlowDueToUsingItem();
    }

    @Redirect(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/Input;shiftKeyDown:Z"))
    private boolean barched$aiStep(Input input) {
        return input.shiftKeyDown || this.isSlowDueToUsingItem() && !this.isPassenger() || this.input.down;
    }

    @Override
    public void applyInput() {
        if (this.isControlledCamera()) {
            Vec2 vec2 = this.modifyInput(this.input.getMoveVector());
            this.xxa = vec2.x;
            this.zza = vec2.y;
            this.jumping = this.input.jumping;
            this.yBobO = this.yBob;
            this.xBobO = this.xBob;
            this.xBob += (this.getXRot() - this.xBob) * 0.5F;
            this.yBob += (this.getYRot() - this.yBob) * 0.5F;
        } else {
            this.super$applyInput();
        }
    }

    @Unique
    private Vec2 modifyInput(Vec2 vec2) {
        if (vec2.lengthSquared() == 0.0F) {
            return vec2;
        } else {
            Vec2 vec22 = vec2.scale(0.98F);
            if (this.isUsingItem() && !this.isPassenger()) {
                vec22 = vec22.scale(this.itemUseSpeedMultiplier());
            }

            if (this.isMovingSlowly()) {
                float f = (float)this.getAttributeValue(Attributes.SNEAKING_SPEED);
                vec22 = vec22.scale(f);
            }

            return modifyInputSpeedForSquareMovement(vec22);
        }
    }

    @Unique
    private static Vec2 modifyInputSpeedForSquareMovement(Vec2 vec2) {
        float f = vec2.length();
        if (f <= 0.0F) {
            return vec2;
        } else {
            Vec2 vec22 = vec2.scale(1.0F / f);
            float g = distanceToUnitSquare(vec22);
            float h = Math.min(f * g, 1.0F);
            return vec22.scale(h);
        }
    }

    @Unique
    private static float distanceToUnitSquare(Vec2 vec2) {
        float f = Math.abs(vec2.x);
        float g = Math.abs(vec2.y);
        float h = g > f ? f / g : g / f;
        return Mth.sqrt(1.0F + Mth.square(h));
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
