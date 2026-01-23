package zzik2.barched.mixin.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import zzik2.barched.Barched;
import zzik2.zreflex.mixin.ModifyAccess;


@Mixin(Items.class)
public abstract class ItemsMixin {

    @Shadow
    public static Item registerItem(String string, Item arg) {
        return null;
    }

    @ModifyArgs(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=zombie_horse_spawn_egg")), at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/SpawnEggItem;<init>(Lnet/minecraft/world/entity/EntityType;IILnet/minecraft/world/item/Item$Properties;)V"))
    private static void barched$modifyEggColor(Args args) {
        if (args.get(0) == EntityType.ZOMBIE_HORSE) {
            args.set(1, 0xFFFFFF);
            args.set(2, 0xFFFFFF);
        }
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item PARCHED_SPAWN_EGG = registerItem("parched_spawn_egg", new SpawnEggItem(Barched.EntityType.PARCHED, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item CAMEL_HUSK_SPAWN_EGG = registerItem("camel_husk_spawn_egg", new SpawnEggItem(Barched.EntityType.CAMEL_HUSK, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item WOODEN_SPEAR = registerItem("wooden_spear", new SpearItem(Tiers.WOOD, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 0.65F, 0.7F, 0.75F, 5.0F, 14.0F, 10.0F, 5.1F, 15.0F, 4.6F))));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item STONE_SPEAR = registerItem("stone_spear", new SpearItem(Tiers.STONE, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 0.75F, 0.82F, 0.7F, 4.5F, 10.0F, 9.0F, 5.1F, 13.75F, 4.6F))));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item COPPER_SPEAR = registerItem("copper_spear", new SpearItem(Barched.Tiers.COPPER, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 0.85F, 0.82F, 0.65F, 4.0F, 9.0F, 8.25F, 5.1F, 12.5F, 4.6F))));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item IRON_SPEAR = registerItem("iron_spear", new SpearItem(Tiers.IRON, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 0.95F, 0.95F, 0.6F, 2.5F, 8.0F, 6.75F, 5.1F, 11.25F, 4.6F))));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item GOLDEN_SPEAR = registerItem("golden_spear", new SpearItem(Tiers.GOLD, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 0.95F, 0.7F, 0.7F, 3.5F, 10.0F, 8.5F, 5.1F, 13.75F, 4.6F))));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item DIAMOND_SPEAR = registerItem("diamond_spear", new SpearItem(Tiers.DIAMOND, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 1.05F, 1.075F, 0.5F, 3.0F, 7.5F, 6.5F, 5.1F, 10.0F, 4.6F))));

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final Item NETHERITE_SPEAR = registerItem("netherite_spear", new SpearItem(Tiers.NETHERITE, (new Item.Properties()).attributes(SpearItem.createAttributes(Tiers.WOOD, 1.15F, 1.2F, 0.4F, 2.5F, 7.0F, 5.5F, 5.1F, 8.75F, 4.6F))));

}
