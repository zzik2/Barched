package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.client.HumanoidModelBridge;

@Mixin(SkeletonModel.class)
public abstract class SkeletonModelMixin<T extends Mob & RangedAttackMob> extends HumanoidModel<T> implements HumanoidModelBridge<T> {

    public SkeletonModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm, @Nullable ArmPose fallback) {
        return livingEntity.getMainArm() == humanoidArm && livingEntity.isAggressive() && livingEntity.getMainHandItem().is(Items.BOW) ? HumanoidModel.ArmPose.BOW_AND_ARROW : super$getArmPose(livingEntity, humanoidArm, getArmPoseFallback());
    }

    @Override
    public @Nullable ArmPose getArmPoseFallback() {
        return null;
    }
}
