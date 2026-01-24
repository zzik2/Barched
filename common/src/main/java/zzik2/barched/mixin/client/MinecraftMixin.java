package zzik2.barched.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.PiercingWeapon;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.client.MultiPlayerGameModeBridge;
import zzik2.barched.bridge.entity.PlayerBridge;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

    @Shadow @Nullable public ClientLevel level;

    @Shadow @Nullable public MultiPlayerGameMode gameMode;

    @Shadow @Nullable public HitResult hitResult;

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), cancellable = true)
    private void barched$startAttack(CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.isItemEnabled(this.level.enabledFeatures())) {
            if (((PlayerBridge) this.player).cannotAttackWithItem(itemStack, 0)) {
                cir.setReturnValue(false);
            } else {
                PiercingWeapon piercingWeapon = (PiercingWeapon)itemStack.get(Barched.DataComponents.PIERCING_WEAPON);
                if (piercingWeapon != null && !(this.gameMode.getPlayerMode() == GameType.SPECTATOR)) {
                    ((MultiPlayerGameModeBridge) this.gameMode).piercingAttack(piercingWeapon);
                    this.player.swing(InteractionHand.MAIN_HAND);
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Redirect(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V"))
    private void barched$attack(MultiPlayerGameMode instance, Player arg, Entity arg2) {
        ItemStack itemStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        AttackRange attackRange = (AttackRange)itemStack.get(Barched.DataComponents.ATTACK_RANGE);
        if (attackRange == null || attackRange.isInRange(this.player, this.hitResult.getLocation())) {
            this.gameMode.attack(this.player, ((EntityHitResult)this.hitResult).getEntity());
        }
    }

    @ModifyExpressionValue(method = "continueAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal = 0))
    private boolean barched$continueAttack(boolean original) {
        ItemStack itemStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        return original && !itemStack.has(Barched.DataComponents.PIERCING_WEAPON);
    }
}
