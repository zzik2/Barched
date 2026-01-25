package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import zzik2.barched.bridge.client.AnimationUtilsBridge;
import zzik2.barched.bridge.item.ItemStackBridge;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(AnimationUtils.class)
public abstract class AnimationUtilsMixin implements AnimationUtilsBridge {

    @Shadow
    public static void animateZombieArms(ModelPart arg, ModelPart arg2, boolean bl, float f, float g) {
    }

    @Shadow
    public static void bobArms(ModelPart arg, ModelPart arg2, float f) {
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Unique
    private static <T extends LivingEntity> void barched$animateZombieArms(ModelPart modelPart, ModelPart modelPart2, boolean bl, float f, float g, T livingEntity) {
        ItemStack itemStack = livingEntity.getMainHandItem();
        SwingAnimationType animationType = ((ItemStackBridge) (Object) itemStack).getSwingAnimation().type();
        boolean bl2 = animationType != SwingAnimationType.STAB;

        if (bl2) {
            animateZombieArms(modelPart, modelPart2, bl, f, g);
            return;
        }

        bobArms(modelPart2, modelPart, g);
    }

    @Override
    public <T extends LivingEntity> void barched$animateZombieArms0(ModelPart modelPart, ModelPart modelPart2, boolean bl, float f, float g, T livingEntity) {
        barched$animateZombieArms(modelPart, modelPart2, bl, f, g, livingEntity);
    }
}
