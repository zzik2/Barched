package zzik2.barched.mixin.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SpearApproach;
import net.minecraft.world.entity.ai.behavior.SpearAttack;
import net.minecraft.world.entity.ai.behavior.SpearRetreat;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {

    @ModifyArg(method = "initFightActivity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/Brain;addActivityAndRemoveMemoryWhenStopped(Lnet/minecraft/world/entity/schedule/Activity;ILcom/google/common/collect/ImmutableList;Lnet/minecraft/world/entity/ai/memory/MemoryModuleType;)V"), index = 2)
    private static ImmutableList<? extends BehaviorControl<? super Piglin>> barched$initFightActivity(ImmutableList<? extends BehaviorControl<? super Piglin>> immutableList) {
        List<BehaviorControl<? super Piglin>> modifiedList = new ArrayList<>(immutableList);
        modifiedList.add(3, new SpearApproach(1.0D, 10.0F));
        modifiedList.add(4, new SpearAttack(1.0D, 1.0D, 10.0F, 2.0F));
        modifiedList.add(5, new SpearRetreat(1.0D));
        return ImmutableList.copyOf(modifiedList);
    }
}
