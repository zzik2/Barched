package zzik2.barched.mixin.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.PiercingWeapon;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.function.UnaryOperator;

@Mixin(DataComponents.class)
public abstract class DataComponentsMixin {

    @Shadow
    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<Float> MINIMUM_ATTACK_CHARGE = register("minimum_attack_charge", (builder) -> {
        return builder.persistent(ZReflectionTool.invokeStaticMethod(ExtraCodecs.class, "floatRange", 0.0F, 1.0F)).networkSynchronized(ByteBufCodecs.FLOAT);
    });

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<PiercingWeapon> PIERCING_WEAPON = register("piercing_weapon", (builder) -> {
        return builder.persistent(PiercingWeapon.CODEC).networkSynchronized(PiercingWeapon.STREAM_CODEC).cacheEncoding();
    });

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<AttackRange> ATTACK_RANGE = register("attack_range", (builder) -> {
        return builder.persistent(AttackRange.CODEC).networkSynchronized(AttackRange.STREAM_CODEC).cacheEncoding();
    });
}
