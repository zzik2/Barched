package zzik2.barched.mixin.component;

import net.minecraft.core.component.DataComponentType;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.component.DataComponentTypeBridge;

@Mixin(DataComponentType.class)
public interface DataComponentTypeMixin extends DataComponentTypeBridge {
}
