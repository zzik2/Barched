package zzik2.barched.mixin.accessor.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {

    @Invoker("pick")
    HitResult invokePick(Entity entity, double d, double e, float f);

    @Invoker("filterHitResult")
    static HitResult invokeFilterHitResult(HitResult hitResult, Vec3 vec3, double d) {
        throw new AssertionError();
    }
}
