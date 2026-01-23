package zzik2.barched.mixin.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {

    @Inject(method = "method_51318", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;HUSK_SPAWN_EGG:Lnet/minecraft/world/item/Item;", opcode = Opcodes.GETSTATIC, shift = At.Shift.AFTER))
    private static void barched$acceptSpawnEgg(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        output.accept(Barched.Items.PARCHED_SPAWN_EGG);
        output.accept(Barched.Items.CAMEL_HUSK_SPAWN_EGG);
    }

    @Inject(method = "method_51325", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;NETHERITE_SWORD:Lnet/minecraft/world/item/Item;", opcode = Opcodes.GETSTATIC, shift = At.Shift.AFTER))
    private static void barched$acceptCombat(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        output.accept(Barched.Items.WOODEN_SPEAR);
        output.accept(Barched.Items.STONE_SPEAR);
        output.accept(Barched.Items.COPPER_SPEAR);
        output.accept(Barched.Items.IRON_SPEAR);
        output.accept(Barched.Items.GOLDEN_SPEAR);
        output.accept(Barched.Items.DIAMOND_SPEAR);
        output.accept(Barched.Items.NETHERITE_SPEAR);
    }
}
