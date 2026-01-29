package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.BarchedClient;

@Mixin(IllagerModel.class)
public abstract class IllagerModelMixin<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {

    @Shadow @Final private ModelPart rightArm;
    @Shadow @Final private ModelPart leftArm;
    @Unique private T barched$illager;
    @Unique private float barched$h;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/AbstractIllager;FFFFF)V", at = @At("HEAD"))
    private void barched$setupAnim(T abstractIllager, float f, float g, float h, float i, float j, CallbackInfo ci) {
        this.barched$illager = abstractIllager;
        this.barched$h = h;
    }

    @Redirect(method = "setupAnim(Lnet/minecraft/world/entity/monster/AbstractIllager;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/AnimationUtils;animateZombieArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;ZFF)V"))
    private void barched$setupAnim(ModelPart h, ModelPart i, boolean j, float arg, float arg2) {
        BarchedClient.AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, barched$h, (LivingEntity) barched$illager);
    }

}
