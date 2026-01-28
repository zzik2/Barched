package zzik2.barched.mixin.item.enchantment;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zzik2.barched.Barched;
import zzik2.barched.bridge.EnchantmentBridge;
import zzik2.zreflex.mixin.ModifyAccess;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.lang.reflect.InvocationHandler;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static void doLungeEffects(ServerLevel serverLevel, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            Barched.EnchantmentHelper.runIterationOnItem(entity.getWeaponItem(), EquipmentSlot.MAINHAND, livingEntity, (holder, i, enchantedItemInUse) -> {
                ((EnchantmentBridge) (Object) holder.value()).doLunge(serverLevel, i, enchantedItemInUse, entity);
            });
        }
    }
}
