package zzik2.barched.mixin.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.entity.EntityBridge;

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

    @Override
    public void barched$snapTo(double d, double e, double f, float g, float h) {
        this.setPosRaw(d, e, f);
        this.setYRot(g);
        this.setXRot(h);
        this.setOldPosAndRot();
        this.reapplyPosition();
    }

    @Override
    public Vec3 getHeadLookAngle() {
        return this.calculateViewVector(this.getXRot(), this.getYHeadRot());
    }
}
