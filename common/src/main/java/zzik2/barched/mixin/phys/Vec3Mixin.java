package zzik2.barched.mixin.phys;

import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import zzik2.barched.bridge.Vec3Bridge;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(Vec3.class)
public abstract class Vec3Mixin implements Vec3Bridge, Position {

    @Shadow @Final public double x;

    @Shadow @Final public double z;

    @Shadow @Final public double y;

    @Override
    public Vec3 addLocalCoordinates(Vec3 vec3) {
        return applyLocalCoordinatesToRotation(this.rotation(), vec3);
    }

    @Unique
    public Vec2 rotation() {
        float f = (float)Math.atan2(-this.x, this.z) * 57.295776F;
        float g = (float)Math.asin(-this.y / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z)) * 57.295776F;
        return new Vec2(g, f);
    }

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static Vec3 applyLocalCoordinatesToRotation(Vec2 vec2, Vec3 vec3) {
        float f = Mth.cos(((vec2.y + 90.0F) * 0.017453292F));
        float g = Mth.sin(((vec2.y + 90.0F) * 0.017453292F));
        float h = Mth.cos((-vec2.x * 0.017453292F));
        float i = Mth.sin((-vec2.x * 0.017453292F));
        float j = Mth.cos(((-vec2.x + 90.0F) * 0.017453292F));
        float k = Mth.sin(((-vec2.x + 90.0F) * 0.017453292F));
        Vec3 vec32 = new Vec3((double)(f * h), (double)i, (double)(g * h));
        Vec3 vec33 = new Vec3((double)(f * j), (double)k, (double)(g * j));
        Vec3 vec34 = vec32.cross(vec33).scale(-1.0D);
        double d = vec32.x * vec3.z + vec33.x * vec3.y + vec34.x * vec3.x;
        double e = vec32.y * vec3.z + vec33.y * vec3.y + vec34.y * vec3.x;
        double l = vec32.z * vec3.z + vec33.z * vec3.y + vec34.z * vec3.x;
        return new Vec3(d, e, l);
    }
}
