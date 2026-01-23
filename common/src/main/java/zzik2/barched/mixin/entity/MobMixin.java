package zzik2.barched.mixin.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.bridge.MobBridge;
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
