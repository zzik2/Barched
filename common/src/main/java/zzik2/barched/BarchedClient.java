package zzik2.barched;

import net.minecraft.client.model.HumanoidModel;
import zzik2.zreflex.enumeration.ZEnumTool;

public class BarchedClient {

    public static void init() {
        //init fields
        Object o = ArmPose.SPEAR;
    }

    public static class ArmPose {

        public static final HumanoidModel.ArmPose SPEAR = ZEnumTool.addConstant(HumanoidModel.ArmPose.class, "SPEAR", new Class<?>[] {boolean.class}, false);
    }
}
