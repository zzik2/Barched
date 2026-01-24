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

            //TODO: find better than this way
            Class<?> visitorInterface = ZReflectionTool.findDeclaredClass(EnchantmentHelper.class, "EnchantmentInSlotVisitor");
            InvocationHandler handler = (proxy, method, args) -> {
                Object holder = args[0];
                Enchantment enchantment = ZReflectionTool.invokeMethod(holder, "value");
                (((EnchantmentBridge) (Object) enchantment)).doLunge(serverLevel, (int) args[1], (EnchantedItemInUse) args[2], entity);
                return null;
            };
            Object visitorProxy = ZReflectionTool.createProxy(visitorInterface, handler);
            ZReflectionTool.invokeStaticMethod(EnchantmentHelper.class, "runIterationOnItem", new Class<?>[]{ItemStack.class, EquipmentSlot.class, LivingEntity.class, visitorInterface}, entity.getWeaponItem(), EquipmentSlot.MAINHAND, livingEntity, visitorProxy);
        }
    }
}
