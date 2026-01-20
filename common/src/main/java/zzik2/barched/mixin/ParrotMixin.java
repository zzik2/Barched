package zzik2.barched.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.zreflex.mixin.ModifyAccess;


import java.util.Map;

@Mixin(Parrot.class)
public class ParrotMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Shadow @Final @Deprecated static Map<EntityType<?>, SoundEvent> MOB_SOUND_MAP;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void barched$addImitationSounds(CallbackInfo ci) {
        MOB_SOUND_MAP.put(Barched.EntityType.PARCHED, Barched.SoundEvents.PARROT_IMITATE_PARCHED);
        MOB_SOUND_MAP.put(Barched.EntityType.CAMEL_HUSK, Barched.SoundEvents.PARROT_IMITATE_CAMEL_HUSK);
    }
}
