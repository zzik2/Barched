package zzik2.barched.mixin.item;

import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.item.ItemStackBridge;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackBridge, DataComponentHolder {

}
