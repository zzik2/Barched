package zzik2.barched.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Mixin(EntityTypeTagsProvider.class)
public abstract class EntityTypeTagsProviderMixin extends IntrinsicHolderTagsProvider<EntityType<?>>{

    public EntityTypeTagsProviderMixin(PackOutput packOutput, ResourceKey<? extends Registry<EntityType<?>>> resourceKey, CompletableFuture<HolderLookup.Provider> completableFuture, Function<EntityType<?>, ResourceKey<EntityType<?>>> function) {
        super(packOutput, resourceKey, completableFuture, function);
    }

    @Inject(method = "addTags", at = @At("TAIL"))
    private void barched$addCustomTags(HolderLookup.Provider provider, CallbackInfo ci) {
        this.tag(EntityTypeTags.SKELETONS).add(new EntityType[]{Barched.EntityType.PARCHED});
        this.tag(EntityTypeTags.ZOMBIES).add(new EntityType[]{Barched.EntityType.CAMEL_HUSK});
    }
}
