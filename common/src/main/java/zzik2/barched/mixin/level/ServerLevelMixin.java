package zzik2.barched.mixin.level;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.level.LevelBridge;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements LevelBridge {

    @Shadow @Final private MinecraftServer server;

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Override
    public void playSeededSound(@Nullable Entity entity, double d, double e, double f, Holder<SoundEvent> holder, SoundSource soundSource, float g, float h, long l) {
        PlayerList var10000 = this.server.getPlayerList();
        Player var10001;
        if (entity instanceof Player) {
            Player player = (Player)entity;
            var10001 = player;
        } else {
            var10001 = null;
        }

        var10000.broadcast(var10001, d, e, f, (double)((SoundEvent)holder.value()).getRange(g), this.dimension(), new ClientboundSoundPacket(holder, soundSource, d, e, f, g, h, l));
    }

    @Override
    public void playSeededSound(@Nullable Entity entity, Entity entity2, Holder<SoundEvent> holder, SoundSource soundSource, float f, float g, long l) {
        PlayerList var10000 = this.server.getPlayerList();
        Player var10001;
        if (entity instanceof Player) {
            Player player = (Player)entity;
            var10001 = player;
        } else {
            var10001 = null;
        }

        var10000.broadcast(var10001, entity2.getX(), entity2.getY(), entity2.getZ(), (holder.value()).getRange(f), this.dimension(), new ClientboundSoundEntityPacket(holder, soundSource, entity2, f, g, l));
    }
}
