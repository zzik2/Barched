package zzik2.barched.mixin.level;

import net.minecraft.util.Mth;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.level.WorldBorderBridge;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin implements WorldBorderBridge {

    @Shadow public abstract double getMinX();

    @Shadow public abstract double getMaxX();

    @Shadow public abstract double getMinZ();

    @Shadow public abstract double getMaxZ();

    @Override
    public Vec3 clampVec3ToBound(Vec3 vec3) {
        return this.clampVec3ToBound(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public Vec3 clampVec3ToBound(double d, double e, double f) {
        return new Vec3(Mth.clamp(d, this.getMinX(), this.getMaxX() - 9.999999747378752E-6D), e, Mth.clamp(f, this.getMinZ(), this.getMaxZ() - 9.999999747378752E-6D));
    }
}
