package zzik2.barched.mixin.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.ArrayList;
import java.util.List;

@Mixin(SmithingTemplateItem.class)
public class SmithingTemplateItemMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ResourceLocation EMPTY_SLOT_SPEAR = ResourceLocation.withDefaultNamespace("container/slot/spear");

    @Inject(method = "createNetheriteUpgradeIconList", at = @At("RETURN"), cancellable = true)
    private static void barched$addIcons(CallbackInfoReturnable<List<ResourceLocation>> cir) {
        List<ResourceLocation> originalList = cir.getReturnValue();
        List<ResourceLocation> newList = new ArrayList<>(originalList);
        newList.add(EMPTY_SLOT_SPEAR);
        cir.setReturnValue(newList);
    }
}
