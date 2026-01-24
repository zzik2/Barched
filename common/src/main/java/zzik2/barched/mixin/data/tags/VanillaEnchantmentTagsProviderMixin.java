package zzik2.barched.mixin.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.data.tags.VanillaEnchantmentTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

import java.util.concurrent.CompletableFuture;

@Mixin(VanillaEnchantmentTagsProvider.class)
public abstract class VanillaEnchantmentTagsProviderMixin extends EnchantmentTagsProvider {

    public VanillaEnchantmentTagsProviderMixin(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture);
    }

    @ModifyArg(method = "addTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/tags/VanillaEnchantmentTagsProvider;tooltipOrder(Lnet/minecraft/core/HolderLookup$Provider;[Lnet/minecraft/resources/ResourceKey;)V"), index = 1)
    private ResourceKey<?>[] barched$addTags(ResourceKey<?>[] original) {
        ResourceKey<?>[] newArray = new ResourceKey<?>[original.length + 1];
        int insertIndex = -1;
        for (int i = 0; i < original.length; i++) {
            if (original[i] == Enchantments.FROST_WALKER) {
                insertIndex = i + 1;
                break;
            }
        }

        System.arraycopy(original, 0, newArray, 0, insertIndex);
        newArray[insertIndex] = Barched.Enchantments.LUNGE;
        System.arraycopy(original, insertIndex - 1, newArray, insertIndex + 1, original.length - (insertIndex - 1));

        return newArray;
    }

    @Inject(method = "addTags", at = @At("TAIL"))
    private void barched$addTags(HolderLookup.Provider provider, CallbackInfo ci) {
        this.tag(EnchantmentTags.NON_TREASURE).add(Barched.Enchantments.LUNGE);
    }
}
