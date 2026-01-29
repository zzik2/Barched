package zzik2.barched.mixin.client.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.item.component.PiercingWeapon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.client.MultiPlayerGameModeBridge;
import zzik2.barched.bridge.entity.PlayerBridge;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin implements MultiPlayerGameModeBridge {

    @Shadow
    protected abstract void ensureHasSentCarriedItem();

    @Shadow @Final private ClientPacketListener connection;

    @Shadow @Final private Minecraft minecraft;

    @Override
    public void piercingAttack(PiercingWeapon piercingWeapon) {
        this.ensureHasSentCarriedItem();
        this.connection.send(new ServerboundPlayerActionPacket(Barched.ServerboundPlayerActionPacket$Action.STAB, BlockPos.ZERO, Direction.DOWN));
        ((PlayerBridge) this.minecraft.player).onAttack();
        ((PlayerBridge) this.minecraft.player).lungeForwardMaybe();
        piercingWeapon.makeSound(this.minecraft.player);
    }
}
