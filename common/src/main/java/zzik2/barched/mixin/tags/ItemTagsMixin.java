package zzik2.barched.mixin.tags;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.zreflex.mixin.ModifyAccess;


@Mixin(ItemTags.class)
public abstract class ItemTagsMixin {

    @Shadow
    private static TagKey<Item> bind(String string) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final TagKey<Item> CAMEL_HUSK_FOOD = bind("camel_husk_food");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final TagKey<Item> ZOMBIE_HORSE_FOOD = bind("zombie_horse_food");

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final TagKey<Item> SPEARS = bind("spears");
}