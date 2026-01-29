package zzik2.barched.mixin.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

import java.util.concurrent.CompletableFuture;

@Mixin(DamageTypeTagsProvider.class)
public abstract class DamageTypeTagsProviderMixin extends TagsProvider<DamageType> {

    protected DamageTypeTagsProviderMixin(PackOutput packOutput, ResourceKey<? extends Registry<DamageType>> resourceKey, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, resourceKey, completableFuture);
    }

    @Inject(method = "addTags", at = @At("TAIL"))
    private void barched$addTags(HolderLookup.Provider provider, CallbackInfo ci) {
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(Barched.DamageTypes.SPEAR);
        this.tag(DamageTypeTags.IS_PLAYER_ATTACK).add(Barched.DamageTypes.SPEAR, Barched.DamageTypes.MACE_SMASH);
    }
}
