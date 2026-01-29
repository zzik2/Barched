package zzik2.barched.mixin.damagesource;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

@Mixin(DamageTypes.class)
public interface DamageTypesMixin {

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void barched$bootstrap(BootstrapContext<DamageType> bootstrapContext, CallbackInfo ci) {
        bootstrapContext.register(Barched.DamageTypes.SPEAR, new DamageType("spear", 0.1F));
        bootstrapContext.register(Barched.DamageTypes.MACE_SMASH, new DamageType("mace_smash", 0.1F));
    }
}
