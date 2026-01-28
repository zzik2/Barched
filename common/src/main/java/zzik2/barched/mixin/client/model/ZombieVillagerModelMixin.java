package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.zreflex.reflection.ZReflectionTool;

@Mixin(ZombieVillagerModel.class)
public abstract class ZombieVillagerModelMixin<T extends Zombie> extends HumanoidModel<T> implements VillagerHeadModel {

    @Unique private T barched$zombie;
    @Unique private float barched$h;

    public ZombieVillagerModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/Zombie;FFFFF)V", at = @At("HEAD"), order = 2000)
    private void barched$setupAnim(T zombie, float f, float g, float h, float i, float j, CallbackInfo ci) {
        this.barched$zombie = zombie;
        this.barched$h = h;
    }

    @Redirect(method = "setupAnim(Lnet/minecraft/world/entity/monster/Zombie;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/AnimationUtils;animateZombieArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;ZFF)V"))
    private void barched$setupAnim(ModelPart h, ModelPart i, boolean j, float arg, float arg2) {
        ZReflectionTool.invokeStaticMethod(AnimationUtils.class, "animateZombieArms", this.leftArm, this.rightArm, barched$zombie.isAggressive(), this.attackTime, barched$h, (LivingEntity) barched$zombie);
    }
}
