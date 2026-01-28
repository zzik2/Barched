package zzik2.barched.mixin.client.renderer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation WOODEN_SPEAR_MODEL = barched$spear("wooden");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation WOODEN_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("wooden");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation STONE_SPEAR_MODEL = barched$spear("stone");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation STONE_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("stone");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation COPPER_SPEAR_MODEL = barched$spear("copper");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation COPPER_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("copper");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation IRON_SPEAR_MODEL = barched$spear("iron");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation IRON_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("iron");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation GOLDEN_SPEAR_MODEL = barched$spear("golden");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation GOLDEN_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("golden");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation DIAMOND_SPEAR_MODEL = barched$spear("diamond");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation DIAMOND_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("diamond");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation NETHERITE_SPEAR_MODEL = barched$spear("netherite");
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ModelResourceLocation NETHERITE_SPEAR_IN_HAND_MODEL = barched$spear_in_hand("netherite");

    @Unique private ItemDisplayContext barched$itemDisplayContext;
    @Unique private ItemStack barched$itemStack;

    @Inject(method = "render", at = @At("HEAD"))
    private void barched$captureItemStack(ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo ci) {
        this.barched$itemStack = itemStack;
        this.barched$itemDisplayContext = itemDisplayContext;
    }

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;getTransforms()Lnet/minecraft/client/renderer/block/model/ItemTransforms;", ordinal = 0, shift = At.Shift.BEFORE), argsOnly = true, index = 8)
    private BakedModel barched$render(BakedModel bakedModel) {
        boolean bl2 = barched$itemDisplayContext == ItemDisplayContext.GUI || barched$itemDisplayContext == ItemDisplayContext.GROUND || barched$itemDisplayContext == ItemDisplayContext.FIXED;
        if (bl2) {
            if (barched$itemStack.is(Barched.Items.WOODEN_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(WOODEN_SPEAR_MODEL);
            } else if (barched$itemStack.is(Barched.Items.STONE_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(STONE_SPEAR_MODEL);
            } else if (barched$itemStack.is(Barched.Items.COPPER_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(COPPER_SPEAR_MODEL);
            } else if (barched$itemStack.is(Barched.Items.IRON_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(IRON_SPEAR_MODEL);
            } else if (barched$itemStack.is(Barched.Items.GOLDEN_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(GOLDEN_SPEAR_MODEL);
            } else if (barched$itemStack.is(Barched.Items.DIAMOND_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(DIAMOND_SPEAR_MODEL);
            } else if (barched$itemStack.is(Barched.Items.NETHERITE_SPEAR)) {
                return this.itemModelShaper.getModelManager().getModel(NETHERITE_SPEAR_MODEL);
            }
        }
        return bakedModel;
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 2))
    private boolean barched$render(boolean original) {
        return original && !barched$itemStack.is(Barched.ItemTags.SPEARS);
    }

    @Redirect(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"))
    private BakedModel barched$getModel(ItemModelShaper instance, ItemStack bakedModel) {
        if (bakedModel.is(Barched.Items.WOODEN_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(WOODEN_SPEAR_IN_HAND_MODEL);
        } else if (bakedModel.is(Barched.Items.STONE_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(STONE_SPEAR_IN_HAND_MODEL);
        } else if (bakedModel.is(Barched.Items.COPPER_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(COPPER_SPEAR_IN_HAND_MODEL);
        } else if (bakedModel.is(Barched.Items.IRON_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(IRON_SPEAR_IN_HAND_MODEL);
        } else if (bakedModel.is(Barched.Items.GOLDEN_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(GOLDEN_SPEAR_IN_HAND_MODEL);
        } else if (bakedModel.is(Barched.Items.DIAMOND_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(DIAMOND_SPEAR_IN_HAND_MODEL);
        } else if (bakedModel.is(Barched.Items.NETHERITE_SPEAR)) {
            return this.itemModelShaper.getModelManager().getModel(NETHERITE_SPEAR_IN_HAND_MODEL);
        }
        return instance.getItemModel(bakedModel);
    }

    @Unique
    private static ModelResourceLocation barched$spear(String prefix) {
        return ModelResourceLocation.inventory(ResourceLocation.withDefaultNamespace(prefix + "_spear"));
    }

    @Unique
    private static ModelResourceLocation barched$spear_in_hand(String prefix) {
        return ModelResourceLocation.inventory(ResourceLocation.withDefaultNamespace(prefix + "_spear_in_hand"));
    }
}
