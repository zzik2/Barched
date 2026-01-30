package zzik2.barched;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import zzik2.barched.bridge.item.ItemStackBridge;
import zzik2.zreflex.enumeration.ZEnumTool;
import zzik2.zreflex.reflection.ZReflectionTool;

public class BarchedClient {

    public static void init() {
        //init fields
        Object o = ArmPose.SPEAR;
    }

    public static class ArmPose {

        public static final HumanoidModel.ArmPose SPEAR = ZEnumTool.addConstant(HumanoidModel.ArmPose.class, "SPEAR", new Class<?>[] {boolean.class}, false);
    }

    public static class SkeletonModel {
        public static LayerDefinition createSingleModelDualBodyLayer() {
            MeshDefinition meshDefinition = new MeshDefinition();
            PartDefinition partDefinition = meshDefinition.getRoot();
            partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F).texOffs(28, 0).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F).texOffs(16, 48).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(42, 33).addBox(-1.55F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(-5.5F, 2.0F, 0.0F));

            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(40, 48).addBox(-1.45F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(5.5F, 2.0F, 0.0F));

            partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(0, 49).addBox(-1.5F, -0.0F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));

            partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(4, 49).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(2.0F, 12.0F, 0.0F));

            return LayerDefinition.create(meshDefinition, 64, 64);
        }
    }

    public static class ModelLayers {

        public static final ModelLayerLocation PARCHED = ZReflectionTool.getStaticFieldValue(net.minecraft.client.model.geom.ModelLayers.class, "PARCHED");
        public static final ModelLayerLocation PARCHED_INNER_ARMOR = ZReflectionTool.getStaticFieldValue(net.minecraft.client.model.geom.ModelLayers.class, "PARCHED_INNER_ARMOR");
        public static final ModelLayerLocation PARCHED_OUTER_ARMOR = ZReflectionTool.getStaticFieldValue(net.minecraft.client.model.geom.ModelLayers.class, "PARCHED_OUTER_ARMOR");
        public static final ModelLayerLocation PARCHED_OUTER_LAYER = ZReflectionTool.getStaticFieldValue(net.minecraft.client.model.geom.ModelLayers.class, "PARCHED_OUTER_LAYER");
    }

    public static class ItemRenderer {
        public static final ModelResourceLocation WOODEN_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "WOODEN_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation STONE_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "STONE_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation COPPER_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "COPPER_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation IRON_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "IRON_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation GOLDEN_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "GOLDEN_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation DIAMOND_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "DIAMOND_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation NETHERITE_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "NETHERITE_SPEAR_IN_HAND_MODEL");
    }

    public static class AnimationUtils {
        public static <T extends LivingEntity> void animateZombieArms(ModelPart modelPart, ModelPart modelPart2, boolean bl, float attackTime, float g, T livingEntity) {
            ItemStack itemStack = livingEntity.getMainHandItem();
            SwingAnimationType animationType = ((ItemStackBridge) (Object) itemStack).getSwingAnimation().type();
            boolean bl2 = animationType != SwingAnimationType.STAB;

            if (bl2) {
                float f2 = attackTime;
                float g2 = -3.1415927F / (bl ? 1.5F : 2.25F);
                float h = Mth.sin((f2 * 3.1415927F));
                float i = Mth.sin(((1.0F - (1.0F - f2) * (1.0F - f2)) * 3.1415927F));
                modelPart2.zRot = 0.0F;
                modelPart2.yRot = -(0.1F - h * 0.6F);
                modelPart2.xRot = g2;
                modelPart2.xRot += h * 1.2F - i * 0.4F;
                modelPart.zRot = 0.0F;
                modelPart.yRot = 0.1F - h * 0.6F;
                modelPart.xRot = g2;
                modelPart.xRot += h * 1.2F - i * 0.4F;
            }

            net.minecraft.client.model.AnimationUtils.bobArms(modelPart2, modelPart, g);
        }
    }
}
