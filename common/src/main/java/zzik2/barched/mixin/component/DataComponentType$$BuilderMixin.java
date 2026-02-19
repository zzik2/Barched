package zzik2.barched.mixin.component;

import net.minecraft.core.component.DataComponentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.bridge.component.DataComponentTypeBridge;
import zzik2.barched.bridge.component.DataComponentType$BuilderBridge;

@Mixin(DataComponentType.Builder.class)
public class DataComponentType$$BuilderMixin<T> implements DataComponentType$BuilderBridge<T> {

    private boolean ignoreSwapAnimation;

    @Override
    public DataComponentType.Builder<T> ignoreSwapAnimation() {
        this.ignoreSwapAnimation = true;
        return (DataComponentType.Builder<T>) (Object) this;
    }

    @Inject(method = "build", at = @At("RETURN"))
    private void barched$onBuild(CallbackInfoReturnable<DataComponentType<T>> cir) {
        if (this.ignoreSwapAnimation) ((DataComponentTypeBridge) cir.getReturnValue()).barched$setIgnoreSwapAnimation(true);
    }
}
