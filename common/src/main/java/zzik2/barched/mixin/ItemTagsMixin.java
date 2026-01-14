package zzik2.barched.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.modifier.ModifyAccess;

@Mixin(ItemTags.class)
public abstract class ItemTagsMixin {

    @Shadow
    private static TagKey<Item> bind(String string) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final TagKey<Item> CAMEL_HUSK_FOOD = bind("camel_husk_food");
}