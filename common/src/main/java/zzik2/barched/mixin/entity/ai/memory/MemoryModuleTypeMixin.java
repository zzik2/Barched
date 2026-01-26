package zzik2.barched.mixin.entity.ai.memory;

import net.minecraft.world.entity.ai.behavior.SpearAttack;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(MemoryModuleType.class)
public abstract class MemoryModuleTypeMixin {

    @Shadow
    private static <U> MemoryModuleType<U> register(String string) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final MemoryModuleType<Integer> SPEAR_FLEEING_TIME = register("spear_fleeing_time");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final MemoryModuleType<Vec3> SPEAR_FLEEING_POSITION = register("spear_fleeing_position");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final MemoryModuleType<Vec3> SPEAR_CHARGE_POSITION = register("spear_charge_position");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final MemoryModuleType<Integer> SPEAR_ENGAGE_TIME = register("spear_engage_time");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final MemoryModuleType<SpearAttack.SpearStatus> SPEAR_STATUS = register("spear_status");
}
