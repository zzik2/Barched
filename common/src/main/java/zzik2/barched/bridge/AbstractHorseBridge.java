package zzik2.barched.bridge;

public interface AbstractHorseBridge {

    default boolean isMobControlled() {
        return false;
    }
}
