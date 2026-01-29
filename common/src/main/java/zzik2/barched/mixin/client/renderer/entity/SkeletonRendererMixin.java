package zzik2.barched.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.client.HumanoidMobRendererBridge;

@Mixin(SkeletonRenderer.class)
public abstract class SkeletonRendererMixin<T extends AbstractSkeleton> extends HumanoidMobRenderer<T, SkeletonModel<T>> implements HumanoidMobRendererBridge<T> {

    public SkeletonRendererMixin(EntityRendererProvider.Context context, SkeletonModel<T> humanoidModel, float f) {
        super(context, humanoidModel, f);
    }

    @Override
    public void render(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        this.getModel().rightArmPose = getArmPose(livingEntity, HumanoidArm.RIGHT);
        this.getModel().leftArmPose = getArmPose(livingEntity, HumanoidArm.LEFT);
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        return livingEntity.getMainArm() == humanoidArm && livingEntity.isAggressive() && livingEntity.getMainHandItem().is(Items.BOW) ? HumanoidModel.ArmPose.BOW_AND_ARROW : super$getArmPose(livingEntity, humanoidArm);
    }
}
