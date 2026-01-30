package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.BarchedClient;
import zzik2.barched.bridge.client.HumanoidModelBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(AbstractZombieModel.class)
public abstract class AbstractZombieModelMixin<T extends Monster> extends HumanoidModel<T> implements HumanoidModelBridge<T> {

    @Shadow
    public abstract boolean isAggressive(T arg);

    @Unique private T barched$monster;
    @Unique private float barched$h;

    public AbstractZombieModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/Monster;FFFFF)V", at = @At("HEAD"), order = 2000)
    private void barched$setupAnim(T monster, float f, float g, float h, float i, float j, CallbackInfo ci) {
        this.barched$monster = monster;
        this.barched$h = h;
    }

    @Redirect(method = "setupAnim(Lnet/minecraft/world/entity/monster/Monster;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/AnimationUtils;animateZombieArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;ZFF)V"))
    private void barched$setupAnim(ModelPart h, ModelPart i, boolean j, float arg, float arg2) {
        BarchedClient.AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(barched$monster), this.attackTime, barched$h, (LivingEntity) barched$monster);
    }

    @Override
    public ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm, HumanoidModel.ArmPose fallback) {
        SwingAnimation swingAnimation = (SwingAnimation)((LivingEntityBridge) livingEntity).getItemHeldByArm(humanoidArm.getOpposite()).get(Barched.DataComponents.SWING_ANIMATION);
        return swingAnimation != null && swingAnimation.type() == SwingAnimationType.STAB ? BarchedClient.ArmPose.SPEAR : super$getArmPose(livingEntity, humanoidArm, getArmPoseFallback());
    }

    @Override
    public @Nullable ArmPose getArmPoseFallback() {
        return ArmPose.EMPTY;
    }
}
