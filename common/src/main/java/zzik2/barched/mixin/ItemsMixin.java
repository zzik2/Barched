package zzik2.barched.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.modifier.ModifyAccess;

@Mixin(Items.class)
public abstract class ItemsMixin {

    @Shadow
    public static Item registerItem(String string, Item arg) {
        return null;
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item PARCHED_SPAWN_EGG = registerItem("parched_spawn_egg", new SpawnEggItem(Barched.EntityType.PARCHED, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item CAMEL_HUSK_SPAWN_EGG = registerItem("camel_husk_spawn_egg", new SpawnEggItem(Barched.EntityType.CAMEL_HUSK, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

}
