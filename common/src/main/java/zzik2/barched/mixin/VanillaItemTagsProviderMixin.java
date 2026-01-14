package zzik2.barched.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

import java.util.concurrent.CompletableFuture;

@Mixin(VanillaItemTagsProvider.class)
public abstract class VanillaItemTagsProviderMixin extends ItemTagsProvider {

    public VanillaItemTagsProviderMixin(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2) {
        super(packOutput, completableFuture, completableFuture2);
    }

    @Inject(method = "addTags", at = @At("TAIL"))
    private void barched$addTags(HolderLookup.Provider provider, CallbackInfo ci) {
        this.tag(Barched.ItemTags.CAMEL_HUSK_FOOD).add(Items.RABBIT_FOOT, Items.PLAYER_HEAD); //hehe XD
    }
}
