package zzik2.barched.mixin.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.bridge.entity.EntityBridge;
import zzik2.barched.bridge.entity.PlayerBridge;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityBridge {

    @Shadow public abstract void setPosRaw(double d, double e, double f);

    @Shadow public abstract void setYRot(float f);

    @Shadow public abstract void setXRot(float f);

    @Shadow public abstract void setOldPosAndRot();

    @Shadow protected abstract void reapplyPosition();

    @Shadow public abstract Vec3 calculateViewVector(float g, float h);

    @Shadow public abstract float getXRot();

    @Shadow public abstract float getYHeadRot();

    @Shadow @Nullable public abstract LivingEntity getControllingPassenger();

    @Shadow public abstract boolean isAlive();

    @Shadow
    public abstract Vec3 position();

    private Vec3 lastKnownSpeed;
    @Nullable private Vec3 lastKnownPosition;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void barched$init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        this.lastKnownSpeed = Vec3.ZERO;
    }

    @Inject(method = "reapplyPosition", at = @At("HEAD"))
    private void barched$reapplyPosition(CallbackInfo ci) {
        this.lastKnownPosition = null;
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V", shift = At.Shift.AFTER, ordinal = 0))
    private void barched$baseTick(CallbackInfo ci) {
        this.computeSpeed();
    }

    @Override
    public void computeSpeed() {
        if (this.lastKnownPosition == null) {
            this.lastKnownPosition = this.position();
        }

        this.lastKnownSpeed = this.position().subtract(this.lastKnownPosition);
        this.lastKnownPosition = this.position();
    }

    @Override
    public Vec3 getHeadLookAngle() {
        return this.calculateViewVector(this.getXRot(), this.getYHeadRot());
    }

    @Override
    public Vec3 getKnownSpeed() {
        LivingEntity var2 = this.getControllingPassenger();
        if (var2 instanceof Player) {
            Player player = (Player)var2;
            if (this.isAlive()) {
                return ((PlayerBridge) player).getKnownSpeed();
            }
        }

        return this.lastKnownSpeed;
    }
}
