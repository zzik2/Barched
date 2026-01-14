package zzik2.barched.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.modifier.ModifyAccess;

@Mixin(AbstractSkeleton.class)
public class AbstractSkeletonMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    @Shadow
    SoundEvent getStepSound() {
        return null;
    }
}
