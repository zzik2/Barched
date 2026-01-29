package zzik2.barched.neoforge.mixin.inventory;

import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;

//why the hell NeoForge is weirdo?#?!?@#$!?@#
@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {

    @Inject(method = "mayPlace", at = @At("RETURN"), cancellable = true)
    private void barched$mayPlace(ItemStack arg, CallbackInfoReturnable<Boolean> cir) {
        if (arg.is(Barched.Items.WOODEN_SPEAR)) {
            cir.setReturnValue(true);
        }
    }
}
