package zzik2.barched.mixin;

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
    private static void barched$accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        output.accept(Barched.Items.PARCHED_SPAWN_EGG);
        output.accept(Barched.Items.CAMEL_HUSK_SPAWN_EGG);
    }
}
