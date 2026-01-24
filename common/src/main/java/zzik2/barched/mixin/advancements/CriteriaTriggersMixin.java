package zzik2.barched.mixin.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.criterion.SpearMobsTrigger;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(CriteriaTriggers.class)
public abstract class CriteriaTriggersMixin {

    @Shadow
    public static <T extends CriterionTrigger<?>> T register(String string, T arg) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SpearMobsTrigger SPEAR_MOBS_TRIGGER = register("spear_mobs", new SpearMobsTrigger());

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void barched$clinit(CallbackInfo ci) {
    }
}
