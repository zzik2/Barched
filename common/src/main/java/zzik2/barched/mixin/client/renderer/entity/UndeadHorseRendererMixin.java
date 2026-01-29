package zzik2.barched.mixin.client.renderer.entity;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.layers.UndeadHorseArmorLayer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UndeadHorseRenderer.class)
public abstract class UndeadHorseRendererMixin extends AbstractHorseRenderer<AbstractHorse, HorseModel<AbstractHorse>> {

    public UndeadHorseRendererMixin(EntityRendererProvider.Context context, HorseModel<AbstractHorse> horseModel, float f) {
        super(context, horseModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void barched$init(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, CallbackInfo ci) {
        this.addLayer(new UndeadHorseArmorLayer((UndeadHorseRenderer) (Object) this, context.getModelSet()));
    }
}
