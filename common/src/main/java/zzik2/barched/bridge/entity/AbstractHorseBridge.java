package zzik2.barched.bridge.entity;

public interface AbstractHorseBridge extends MobBridge {

    default boolean isMobControlled() {
        return false;
    }
}
