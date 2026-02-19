package zzik2.barched.mixin.component;

import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.component.DataComponentTypeBridge;

@Mixin(targets = "net.minecraft.core.component.DataComponentType$Builder$SimpleType")
public class DataComponentType$Builder$SimpleTypeMixin implements DataComponentTypeBridge {

    private boolean ignoreSwapAnimation;

    @Override
    public boolean ignoreSwapAnimation() {
        return this.ignoreSwapAnimation;
    }

    @Override
    public void barched$setIgnoreSwapAnimation(boolean value) {
        this.ignoreSwapAnimation = value;
    }
}
