package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.zreflex.reflection.ZReflectionTool;

@Mixin(PiglinModel.class)
public abstract class PiglinModelMixin<T extends Mob> extends PlayerModel<T> {

    @Unique private T barched$mob;
    @Unique private float barched$h;

    public PiglinModelMixin(ModelPart modelPart, boolean bl) {
        super(modelPart, bl);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/Mob;FFFFF)V", at = @At("HEAD"))
    private void barched$setupAnim(T mob, float f, float g, float h, float i, float j, CallbackInfo ci) {
        this.barched$mob = mob;
        this.barched$h = h;
    }

    @Redirect(method = "setupAnim(Lnet/minecraft/world/entity/Mob;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/AnimationUtils;animateZombieArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;ZFF)V"))
    private void barched$setupAnim(ModelPart h, ModelPart i, boolean j, float arg, float arg2) {
        ZReflectionTool.invokeStaticMethod(AnimationUtils.class, "animateZombieArms", this.leftArm, this.rightArm, barched$mob.isAggressive(), this.attackTime, barched$h, (LivingEntity) barched$mob);
    }
}
