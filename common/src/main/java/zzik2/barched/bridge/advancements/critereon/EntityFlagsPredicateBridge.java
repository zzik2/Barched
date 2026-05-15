package zzik2.barched.bridge.advancements.critereon;

import java.util.Optional;

public interface EntityFlagsPredicateBridge {

    default Optional<Boolean> barched$isInWater() {
        return Optional.empty();
    }

    default Optional<Boolean> barched$isFallFlying() {
        return Optional.empty();
    }

    default void barched$setIsInWater(Optional<Boolean> value) {
    }

    default void barched$setIsFallFlying(Optional<Boolean> value) {
    }
}
