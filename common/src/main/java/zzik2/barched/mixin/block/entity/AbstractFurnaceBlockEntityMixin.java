package zzik2.barched.mixin.block.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.Barched;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow private static void add(Map<Item, Integer> map, ItemLike arg, int i) {}

    @Inject(method = "getFuel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;add(Ljava/util/Map;Lnet/minecraft/world/level/ItemLike;I)V", ordinal = 0, shift = At.Shift.BEFORE))
    private static void barched$getFuel(CallbackInfoReturnable<Map<Item, Integer>> cir, @Local(ordinal = 1) Map<Item, Integer> map2) {
        add(map2, Barched.Items.WOODEN_SPEAR, 200);
    }
}
