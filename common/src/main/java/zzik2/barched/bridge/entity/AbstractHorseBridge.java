package zzik2.barched.bridge.entity;

public interface AbstractHorseBridge {

    default boolean isMobControlled() {
        return false;
    }
}
