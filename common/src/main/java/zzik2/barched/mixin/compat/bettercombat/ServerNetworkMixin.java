package zzik2.barched.mixin.compat.bettercombat;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.bettercombat.api.AttackHand;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.network.Packets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.bridge.entity.PlayerBridge;

/**
 * This compat patches from below:
 * <a href="https://github.com/ZsoltMolnarrr/BetterCombat/commit/03bd9b721f0423950993ba746900e5e66c5d2b4d#diff-1b07c954cf17dcbf51ed8731d008bddaf7a762589e7fa5fd05f212d182539d27">...</a>
 */
@Mixin(net.bettercombat.network.ServerNetwork.class)
public abstract class ServerNetworkMixin {

    @Dynamic
    @Inject(method = "lambda$handleAttackRequest$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;handleInteract(Lnet/minecraft/network/protocol/game/ServerboundInteractPacket;)V", shift = At.Shift.AFTER))
    private static void barched$captureAttack(ServerPlayer player, WeaponAttributes attributes, WeaponAttributes.Attack attack, AttackHand hand, ServerLevel world, Packets.C2S_AttackRequest request, boolean useVanillaPacket, ServerGamePacketListenerImpl handler, CallbackInfo ci, @Share("attackedAnyEntity") LocalBooleanRef attackedAnyEntity) {
        attackedAnyEntity.set(true);
    }

    @Dynamic
    @Inject(method = "lambda$handleAttackRequest$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;attack(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER))
    private static void barched$captureDirectAttack(ServerPlayer player, WeaponAttributes attributes, WeaponAttributes.Attack attack, AttackHand hand, ServerLevel world, Packets.C2S_AttackRequest request, boolean useVanillaPacket, ServerGamePacketListenerImpl handler, CallbackInfo ci, @Share("attackedAnyEntity") LocalBooleanRef attackedAnyEntity) {
        attackedAnyEntity.set(true);
    }

    @Dynamic
    @Inject(method = "lambda$handleAttackRequest$3", at = @At(value = "INVOKE", target = "Lnet/bettercombat/logic/PlayerAttackProperties;setComboCount(I)V", shift = At.Shift.BEFORE))
    private static void barched$applyLunge(ServerPlayer player, WeaponAttributes attributes, WeaponAttributes.Attack attack, AttackHand hand, ServerLevel world, Packets.C2S_AttackRequest request, boolean useVanillaPacket, ServerGamePacketListenerImpl handler, CallbackInfo ci, @Share("attackedAnyEntity") LocalBooleanRef attackedAnyEntity) {
        if (!attackedAnyEntity.get()) {
            ((PlayerBridge) player).lungeForwardMaybe();
        }
    }
}
