package zzik2.barched.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.Barched;
import zzik2.barched.BarchedClient;
import zzik2.barched.bridge.client.HumanoidMobRendererBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(AbstractZombieRenderer.class)
public abstract class AbstractZombieRendererMixin<T extends Zombie, M extends ZombieModel<T>> extends HumanoidMobRenderer<T, M> implements HumanoidMobRendererBridge<T> {

    public AbstractZombieRendererMixin(EntityRendererProvider.Context context, M humanoidModel, float f) {
        super(context, humanoidModel, f);
    }

    @Override
    public void render(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        this.getModel().rightArmPose = this.getArmPose(livingEntity, HumanoidArm.RIGHT);
        this.getModel().leftArmPose = this.getArmPose(livingEntity, HumanoidArm.LEFT);
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm) {
        SwingAnimation swingAnimation = (SwingAnimation) ((LivingEntityBridge) livingEntity).getItemHeldByArm(humanoidArm.getOpposite()).get(Barched.DataComponents.SWING_ANIMATION);
        return swingAnimation != null && swingAnimation.type() == SwingAnimationType.STAB ? BarchedClient.ArmPose.SPEAR : super$getArmPose(livingEntity, humanoidArm);
    }
}
