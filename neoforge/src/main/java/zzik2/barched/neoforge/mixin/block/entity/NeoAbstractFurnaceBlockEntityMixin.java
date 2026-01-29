package zzik2.barched.neoforge.mixin.block.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;

//why the hell NeoForge is weirdo
@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class NeoAbstractFurnaceBlockEntityMixin {

    @Inject(method = "canPlaceItem", at = @At("RETURN"), cancellable = true)
    private void barched$canPlaceItem(int i, ItemStack arg, CallbackInfoReturnable<Boolean> cir) {
        LogUtils.getLogger().info("canPlaceItem");
        if (arg.is(Barched.Items.WOODEN_SPEAR)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getBurnDuration", at = @At("RETURN"), cancellable = true)
    private void barched$getBurnDuration(ItemStack arg, CallbackInfoReturnable<Integer> cir) {
        LogUtils.getLogger().info("getBurnDuration");
        if (arg.is(Barched.Items.WOODEN_SPEAR)) {
            cir.setReturnValue(200);
        }
    }
}
