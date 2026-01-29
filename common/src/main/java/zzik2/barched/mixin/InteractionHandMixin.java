package zzik2.barched.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.bridge.InteractionHandBridge;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

@Mixin(InteractionHand.class)
public class InteractionHandMixin implements InteractionHandBridge {

    @Override
    public EquipmentSlot asEquipmentSlot() {
        return (InteractionHand) (Object) this == MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }
}
