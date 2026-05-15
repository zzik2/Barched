package zzik2.barched.mixin.accessor.client.renderer;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {

    @Invoker("pick")
    HitResult barched$pick(Entity entity, double blockInteractionRange, double entityInteractionRange, float partialTicks);
}
