package net.minecraft.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.CamelHusk;

@Environment(EnvType.CLIENT)
public class CamelHuskRenderer extends MobRenderer<CamelHusk, CamelModel<CamelHusk>> {

    private static final ResourceLocation CAMEL_HUSK_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/camel/camel_husk.png");

    public CamelHuskRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context, new CamelModel(context.bakeLayer(modelLayerLocation)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(CamelHusk  camel) {
        return CAMEL_HUSK_LOCATION;
    }
}
