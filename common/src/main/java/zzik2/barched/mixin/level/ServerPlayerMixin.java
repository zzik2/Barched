package zzik2.barched.mixin.level;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.entity.EntityBridge;
import zzik2.barched.bridge.entity.PlayerBridge;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements PlayerBridge {

    @Shadow private Vec3 lastKnownClientMovement;

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Override
    public Vec3 getKnownSpeed() {
        Entity entity = this.getVehicle();
        return entity != null && entity.getControllingPassenger() != this ? ((EntityBridge) entity).getKnownSpeed() : this.lastKnownClientMovement;
    }
}
