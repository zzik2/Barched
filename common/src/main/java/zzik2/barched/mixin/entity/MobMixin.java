package zzik2.barched.mixin.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.MobBridge;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(Mob.class)
public abstract class MobMixin implements MobBridge {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC, removeFinal = true)
    @Shadow
    public final InteractionResult interact(Player player, InteractionHand interactionHand) {
        return null;
    }

    @Override
    public EquipmentSlot sunProtectionSlot() {
        return EquipmentSlot.HEAD;
    }
}
