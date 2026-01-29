package zzik2.barched.mixin.network;

import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.PiercingWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.PlayerBridge;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow public ServerPlayer player;

    @Inject(method = "handlePlayerAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetLastActionTime()V", shift = At.Shift.AFTER), cancellable = true)
    private void barched$handlePlayerAction(ServerboundPlayerActionPacket serverboundPlayerActionPacket, CallbackInfo ci) {
        ServerboundPlayerActionPacket.Action action = serverboundPlayerActionPacket.getAction();
        if (action.name().equals("STAB")) {
            if (this.player.isSpectator()) {
                ci.cancel();
                return;
            } else {
                ItemStack itemStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
                if (((PlayerBridge) this.player).cannotAttackWithItem(itemStack, 5)) {
                    ci.cancel();
                    return;
                }

                PiercingWeapon piercingWeapon = (PiercingWeapon)itemStack.get(Barched.DataComponents.PIERCING_WEAPON);
                if (piercingWeapon != null) {
                    piercingWeapon.attack(this.player, EquipmentSlot.MAINHAND);
                }

                ci.cancel();
                return;
            }
        }
    }
}
