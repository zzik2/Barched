package zzik2.barched.mixin.sounds;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;


@Mixin(SoundEvents.class)
public abstract class SoundEventsMixin {

    @Shadow
    private static SoundEvent register(String string) {
        return null;
    }

    @Shadow
    private static Holder.Reference<SoundEvent> registerForHolder(String string) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent PARCHED_AMBIENT = register("entity.parched.ambient");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent PARCHED_DEATH = register("entity.parched.death");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent PARCHED_HURT = register("entity.parched.hurt");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent PARCHED_STEP = register("entity.parched.step");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent PARROT_IMITATE_PARCHED = register("entity.parrot.imitate.parched");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent PARROT_IMITATE_CAMEL_HUSK = register("entity.parrot.imitate.camel_husk");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_AMBIENT = register("entity.camel_husk.ambient");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_DASH = register("entity.camel_husk.dash");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_DASH_READY = register("entity.camel_husk.dash_ready");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_DEATH = register("entity.camel_husk.death");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_EAT = register("entity.camel_husk.eat");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_HURT = register("entity.camel_husk.hurt");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_SADDLE = register("entity.camel_husk.saddle");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_SIT = register("entity.camel_husk.sit");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_STAND = register("entity.camel_husk.stand");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_STEP = register("entity.camel_husk.step");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent CAMEL_HUSK_STEP_SAND = register("entity.camel_husk.step_sand");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent ZOMBIE_HORSE_ANGRY = register("entity.zombie_horse.angry");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final SoundEvent ZOMBIE_HORSE_EAT = register("entity.zombie_horse.eat");

    private static final Holder<SoundEvent> LUNGE = registerForHolder("item.spear.lunge");
    private static final Holder<SoundEvent> LUNGE_1 = registerForHolder("item.spear.lunge_1");
    private static final Holder<SoundEvent> LUNGE_2 = registerForHolder("item.spear.lunge_2");
    private static final Holder<SoundEvent> LUNGE_3 = registerForHolder("item.spear.lunge_3");
    private static final Holder<SoundEvent> SPEAR_USE = registerForHolder("item.spear.use");
    private static final Holder<SoundEvent> SPEAR_HIT = registerForHolder("item.spear.hit");
    private static final Holder<SoundEvent> SPEAR_ATTACK = registerForHolder("item.spear.attack");
    private static final Holder<SoundEvent> SPEAR_WOOD_USE = registerForHolder("item.spear_wood.use");
    private static final Holder<SoundEvent> SPEAR_WOOD_HIT = registerForHolder("item.spear_wood.hit");
    private static final Holder<SoundEvent> SPEAR_WOOD_ATTACK = registerForHolder("item.spear_wood.attack");
}
