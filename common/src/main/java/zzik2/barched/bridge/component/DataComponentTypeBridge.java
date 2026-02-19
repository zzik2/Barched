package zzik2.barched.bridge.component;

public interface DataComponentTypeBridge {

    default boolean ignoreSwapAnimation() {
        return false;
    }

    default void barched$setIgnoreSwapAnimation(boolean value) {
    }
}
