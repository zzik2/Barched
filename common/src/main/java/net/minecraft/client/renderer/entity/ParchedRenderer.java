package net.minecraft.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Parched;
import org.jetbrains.annotations.NotNull;
import zzik2.zreflex.reflection.ZReflectionTool;


@Environment(EnvType.CLIENT)
public class ParchedRenderer extends SkeletonRenderer<Parched> {

    private static final ResourceLocation PARCHED_SKELETON_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/skeleton/parched.png");
    private static final ModelLayerLocation PARCHED = ZReflectionTool.getStaticFieldValue(ModelLayers .class, "PARCHED");
    private static final ModelLayerLocation PARCHED_INNER_ARMOR = ZReflectionTool.getStaticFieldValue(ModelLayers.class, "PARCHED_INNER_ARMOR");;
    private static final ModelLayerLocation PARCHED_OUTER_ARMOR = ZReflectionTool.getStaticFieldValue(ModelLayers.class, "PARCHED_OUTER_ARMOR");;
    private static final ModelLayerLocation PARCHED_OUTER_LAYER = ZReflectionTool.getStaticFieldValue(ModelLayers.class, "PARCHED_OUTER_LAYER");;

    public ParchedRenderer(EntityRendererProvider.Context context) {
        super(context, PARCHED, PARCHED_INNER_ARMOR, PARCHED_OUTER_ARMOR);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Parched parched) {
        return PARCHED_SKELETON_LOCATION;
    }
}
