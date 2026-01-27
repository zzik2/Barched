package zzik2.barched;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
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

    public static class ItemRenderer {
        public static final ModelResourceLocation WOODEN_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "WOODEN_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation STONE_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "STONE_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation COPPER_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "COPPER_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation IRON_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "IRON_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation GOLDEN_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "GOLDEN_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation DIAMOND_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "DIAMOND_SPEAR_IN_HAND_MODEL");
        public static final ModelResourceLocation NETHERITE_SPEAR_IN_HAND_MODEL = ZReflectionTool.getStaticFieldValue(net.minecraft.client.renderer.entity.ItemRenderer.class, "NETHERITE_SPEAR_IN_HAND_MODEL");
    }
}
