package zzik2.barched.mixin.client.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.UseEffects;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import zzik2.barched.Barched;
import zzik2.barched.mixin.accessor.client.renderer.GameRendererAccessor;
import zzik2.barched.bridge.client.LocalPlayerBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements LivingEntityBridge, LocalPlayerBridge {

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

    @Override
    public HitResult raycastHitResult(GameRenderer renderer, float f, Entity entity) {
        ItemStack itemStack = this.getActiveItem();
        AttackRange attackRange = (AttackRange)itemStack.get(Barched.DataComponents.ATTACK_RANGE);
        double d = this.blockInteractionRange();
        HitResult hitResult = null;
        if (attackRange != null) {
            hitResult = attackRange.getClosesetHit(entity, f, EntitySelector.NO_SPECTATORS.and(Entity::isPickable));
            if (hitResult instanceof BlockHitResult) {
                hitResult = filterHitResult(hitResult, entity.getEyePosition(f), d);
            }
        }

        if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) {
            hitResult = ((GameRendererAccessor) renderer).barched$pick(entity, d, this.entityInteractionRange(), f);
        }

        return hitResult;
    }

    private static HitResult filterHitResult(HitResult hitResult, Vec3 vec3, double d) {
        Vec3 vec32 = hitResult.getLocation();
        if (!vec32.closerThan(vec3, d)) {
            Vec3 vec33 = hitResult.getLocation();
            Direction direction = Barched.Direction.getApproximateNearest(vec33.x - vec3.x, vec33.y - vec3.y, vec33.z - vec3.z);
            return BlockHitResult.miss(vec33, direction, BlockPos.containing(vec33));
        } else {
            return hitResult;
        }
    }
}
