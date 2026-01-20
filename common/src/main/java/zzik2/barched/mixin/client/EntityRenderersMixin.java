package zzik2.barched.mixin.client;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CamelHuskRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ParchedRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.reflection.ZReflectionTool;


@Mixin(EntityRenderers.class)
public abstract class EntityRenderersMixin {

    @Shadow
    private static <T extends Entity> void register(EntityType<? extends T> arg, EntityRendererProvider<T> arg2) {
    }

    static {
        register(ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.EntityType.class, "PARCHED"), ParchedRenderer::new);
        register(ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.EntityType.class, "CAMEL_HUSK"), context -> new CamelHuskRenderer(context, ModelLayers.CAMEL));
    }
}
