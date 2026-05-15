package zzik2.barched.mixin.advancements.critereon;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.bridge.advancements.critereon.EntityFlagsPredicateBridge;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.Optional;

@Mixin(EntityFlagsPredicate.class)
public abstract class EntityFlagsPredicateMixin implements EntityFlagsPredicateBridge {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC, removeFinal = true)
    @Shadow
    public static Codec<EntityFlagsPredicate> CODEC;

    @Unique private Optional<Boolean> barched$isInWater = Optional.empty();

    @Unique private Optional<Boolean> barched$isFallFlying = Optional.empty();

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void barched$extendCodec(CallbackInfo ci) {
        MapCodec<EntityFlagsPredicate> baseMap = ((MapCodec.MapCodecCodec<EntityFlagsPredicate>) CODEC).codec();

        MapCodec<Pair<Optional<Boolean>, Optional<Boolean>>> extrasMap = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("is_in_water").forGetter(Pair::getFirst),
                Codec.BOOL.optionalFieldOf("is_fall_flying").forGetter(Pair::getSecond)
        ).apply(instance, Pair::of));

        MapCodec<EntityFlagsPredicate> combined = Codec.mapPair(baseMap, extrasMap).xmap(
                pair -> {
                    EntityFlagsPredicate predicate = pair.getFirst();
                    Pair<Optional<Boolean>, Optional<Boolean>> extras = pair.getSecond();
                    EntityFlagsPredicateBridge bridge = (EntityFlagsPredicateBridge) (Object) predicate;
                    bridge.barched$setIsInWater(extras.getFirst());
                    bridge.barched$setIsFallFlying(extras.getSecond());
                    return predicate;
                },
                predicate -> {
                    EntityFlagsPredicateBridge bridge = (EntityFlagsPredicateBridge) (Object) predicate;
                    return Pair.of(predicate, Pair.of(bridge.barched$isInWater(), bridge.barched$isFallFlying()));
                }
        );

        CODEC = combined.codec();
    }

    @Inject(method = "matches", at = @At("RETURN"), cancellable = true)
    private void barched$matches(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        if (this.barched$isInWater.isPresent() && entity.isInWater() != this.barched$isInWater.get()) {
            cir.setReturnValue(false);
            return;
        }

        if (this.barched$isFallFlying.isPresent() && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.isFallFlying() != this.barched$isFallFlying.get()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Override
    public Optional<Boolean> barched$isInWater() {
        return this.barched$isInWater;
    }

    @Override
    public Optional<Boolean> barched$isFallFlying() {
        return this.barched$isFallFlying;
    }

    @Override
    public void barched$setIsInWater(Optional<Boolean> value) {
        this.barched$isInWater = value;
    }

    @Override
    public void barched$setIsFallFlying(Optional<Boolean> value) {
        this.barched$isFallFlying = value;
    }
}
