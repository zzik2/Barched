package zzik2.barched.mixin.component;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.component.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.bridge.component.DataComponentTypeBridge;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.function.UnaryOperator;

@Mixin(DataComponents.class)
public abstract class DataComponentsMixin {

    @Shadow
    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC, removeFinal = true)
    @Shadow public static DataComponentMap COMMON_ITEM_COMPONENTS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void barched$clinit(CallbackInfo ci) {
        DataComponentMap originalMap = COMMON_ITEM_COMPONENTS;
        DataComponentMap newMap = DataComponentMap.builder().addAll(originalMap).set(SWING_ANIMATION, SwingAnimation.DEFAULT).set(USE_EFFECTS, UseEffects.DEFAULT).build();
        COMMON_ITEM_COMPONENTS = newMap;

        ((DataComponentTypeBridge) DataComponents.DAMAGE).barched$setIgnoreSwapAnimation(true);
    }

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<UseEffects> USE_EFFECTS = register("use_effects", (builder) -> {
        return builder.persistent(UseEffects.CODEC).networkSynchronized(UseEffects.STREAM_CODEC);
    });

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<EitherHolder<DamageType>> DAMAGE_TYPE = register("damage_type", (builder) -> {
        return builder.persistent(EitherHolder.codec(Registries.DAMAGE_TYPE, DamageType.CODEC)).networkSynchronized(EitherHolder.streamCodec(Registries.DAMAGE_TYPE, DamageType.STREAM_CODEC));
    });

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<Float> MINIMUM_ATTACK_CHARGE = register("minimum_attack_charge", (builder) -> {
        return builder.persistent(Barched.ExtraCodecs.floatRange(0.0F, 1.0F)).networkSynchronized(ByteBufCodecs.FLOAT);
    });

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<PiercingWeapon> PIERCING_WEAPON = register("piercing_weapon", (builder) -> {
        return builder.persistent(PiercingWeapon.CODEC).networkSynchronized(PiercingWeapon.STREAM_CODEC).cacheEncoding();
    });

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<AttackRange> ATTACK_RANGE = register("attack_range", (builder) -> {
        return builder.persistent(AttackRange.CODEC).networkSynchronized(AttackRange.STREAM_CODEC).cacheEncoding();
    });

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<KineticWeapon> KINETIC_WEAPON = register("kinetic_weapon", (builder) -> {
        return builder.persistent(KineticWeapon.CODEC).networkSynchronized(KineticWeapon.STREAM_CODEC).cacheEncoding();
    });

    @Unique
    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final DataComponentType<SwingAnimation> SWING_ANIMATION = register("swing_animation", (builder) -> {
        return builder.persistent(SwingAnimation.CODEC).networkSynchronized(SwingAnimation.STREAM_CODEC);
    });
}
