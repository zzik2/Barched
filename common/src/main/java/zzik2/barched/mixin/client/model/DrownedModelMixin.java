package zzik2.barched.mixin.client.model;

import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.client.HumanoidModelBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;

@Mixin(DrownedModel.class)
public abstract class DrownedModelMixin<T extends Zombie> extends ZombieModel<T> implements HumanoidModelBridge<T> {

    public DrownedModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public ArmPose getArmPose(T livingEntity, HumanoidArm humanoidArm, @Nullable ArmPose fallback) {
        ItemStack itemStack = ((LivingEntityBridge) livingEntity).getItemHeldByArm(humanoidArm);
        return livingEntity.getMainArm() == humanoidArm && livingEntity.isAggressive() && itemStack.is(Items.TRIDENT) ? ArmPose.THROW_SPEAR : abstractZombie$super$getArmPose(livingEntity, humanoidArm, getArmPoseFallback());
    }

    @Override
    public @Nullable ArmPose getArmPoseFallback() {
        return ArmPose.EMPTY;
    }
}
