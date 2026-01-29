package zzik2.barched.neoforge.mixin.inventory;

import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;

//why the hell NeoForge is weirdo????
@Mixin(AbstractFurnaceMenu.class)
public class AbstractFurnaceMenuMixin {

    @Inject(method = "isFuel", at = @At("RETURN"), cancellable = true)
    private void barchd$isFuel(ItemStack arg, CallbackInfoReturnable<Boolean> cir) {
        if (arg.is(Barched.Items.WOODEN_SPEAR)) {
            cir.setReturnValue(true);
        }
    }
}
