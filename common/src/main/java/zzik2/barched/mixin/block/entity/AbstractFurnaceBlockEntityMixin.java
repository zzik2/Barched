package zzik2.barched.mixin.block.entity;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zzik2.barched.Barched;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow private static void add(Map<Item, Integer> map, ItemLike arg, int i) {}

    // Fabric
    @Dynamic
    @Redirect(method = "getFuel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;add(Ljava/util/Map;Lnet/minecraft/world/level/ItemLike;I)V", ordinal = 0), require = 0)
    private static void barched$getFuel(Map<Item, Integer> map, ItemLike itemLike, int i) {
        add(map, itemLike, i);
        add(map, Barched.Items.WOODEN_SPEAR, 200);
    }
}
