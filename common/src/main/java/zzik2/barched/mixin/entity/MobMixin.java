package zzik2.barched.mixin.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.bridge.entity.MobBridge;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements MobBridge {

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC, removeFinal = true)
    @Shadow
    public final InteractionResult interact(Player player, InteractionHand interactionHand) {
        return null;
    }

    @Shadow protected abstract boolean isSunBurnTick();

    @Inject(method = "doHurtTarget", at = @At("TAIL"))
    private void barched$doHurtTarget(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        this.lungeForwardMaybe();
    }

    @Override
    public EquipmentSlot sunProtectionSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void burnUndead() {
        if (this.isAlive() && this.isSunBurnTick()) {
            EquipmentSlot equipmentSlot = this.sunProtectionSlot();
            ItemStack itemStack = this.getItemBySlot(equipmentSlot);
            if (!itemStack.isEmpty()) {
                if (itemStack.isDamageableItem()) {
                    Item item = itemStack.getItem();
                    itemStack.setDamageValue(itemStack.getDamageValue() + this.random.nextInt(2));
                    if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
                        this.onEquippedItemBroken(item, equipmentSlot);
                        this.setItemSlot(equipmentSlot, ItemStack.EMPTY);
                    }
                }

            } else {
                this.igniteForSeconds(8.0F);
            }
        }
    }
}
