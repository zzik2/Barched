package zzik2.barched.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.client.HumanoidMobRendererBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(DrownedRenderer.class)
public abstract class DrownedRendererMixin extends AbstractZombieRenderer<Drowned, DrownedModel<Drowned>> implements HumanoidMobRendererBridge<Drowned> {

    protected DrownedRendererMixin(EntityRendererProvider.Context context, DrownedModel<Drowned> zombieModel, DrownedModel<Drowned> zombieModel2, DrownedModel<Drowned> zombieModel3) {
        super(context, zombieModel, zombieModel2, zombieModel3);
    }

    @Override
    public void render(Drowned livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(Drowned drowned, HumanoidArm humanoidArm) {
        ItemStack itemStack = ((LivingEntityBridge) drowned).getItemHeldByArm(humanoidArm);
        return drowned.getMainArm() == humanoidArm && drowned.isAggressive() && itemStack.is(Items.TRIDENT) ? HumanoidModel.ArmPose.THROW_SPEAR : super$getArmPose(drowned, humanoidArm);
    }
}
