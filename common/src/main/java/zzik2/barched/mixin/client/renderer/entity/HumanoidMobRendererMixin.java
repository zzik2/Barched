package zzik2.barched.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import zzik2.barched.Barched;
import zzik2.barched.BarchedClient;
import zzik2.barched.bridge.client.HumanoidMobRendererBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin<T extends Mob, M extends HumanoidModel<T>> extends MobRenderer<T, M> implements HumanoidMobRendererBridge<T> {

    public HumanoidMobRendererMixin(EntityRendererProvider.Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    public void render(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        this.getModel().rightArmPose = getArmPose(livingEntity, HumanoidArm.RIGHT);
        this.getModel().leftArmPose = getArmPose(livingEntity, HumanoidArm.LEFT);
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public HumanoidModel.ArmPose super$getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        return this.barched$getArmPose(livingEntity, humanoidArm);
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        return this.barched$getArmPose(livingEntity, humanoidArm);
    }

    @Unique
    public HumanoidModel.ArmPose barched$getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        ItemStack itemStack = ((LivingEntityBridge) livingEntity).getItemHeldByArm(humanoidArm);
        SwingAnimation swingAnimation = (SwingAnimation)itemStack.get(Barched.DataComponents.SWING_ANIMATION);
        if (swingAnimation != null && swingAnimation.type() == SwingAnimationType.STAB && livingEntity.swinging) {
            return BarchedClient.ArmPose.SPEAR;
        } else {
            return itemStack.is(Barched.ItemTags.SPEARS) ? BarchedClient.ArmPose.SPEAR : HumanoidModel.ArmPose.EMPTY;
        }
    }
}
