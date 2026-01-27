package zzik2.barched.mixin.client.model;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.BarchedClient;

import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow
    protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation arg);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1))
    private void barched$init(BlockColors blockColors, ProfilerFiller profilerFiller, Map map, Map map2, CallbackInfo ci) {
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.WOODEN_SPEAR_IN_HAND_MODEL);
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.STONE_SPEAR_IN_HAND_MODEL);
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.COPPER_SPEAR_IN_HAND_MODEL);
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.IRON_SPEAR_IN_HAND_MODEL);
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.GOLDEN_SPEAR_IN_HAND_MODEL);
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.DIAMOND_SPEAR_IN_HAND_MODEL);
        this.loadSpecialItemModelAndDependencies(BarchedClient.ItemRenderer.NETHERITE_SPEAR_IN_HAND_MODEL);
    }
}
