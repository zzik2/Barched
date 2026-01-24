package zzik2.barched.mixin.item.enchantment;

import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.zreflex.mixin.ModifyAccess;

import java.util.List;

@Mixin(Enchantments.class)
public abstract class EnchantmentsMixin {

    @Shadow
    private static ResourceKey<Enchantment> key(String string) {
        return null;
    }

    @Shadow
    private static void register(BootstrapContext<Enchantment> arg, ResourceKey<Enchantment> arg2, Enchantment.Builder arg3) {
    }

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ResourceKey<Enchantment> LUNGE = key("lunge");

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void barched$bootstrap(BootstrapContext<Enchantment> bootstrapContext, CallbackInfo ci) {
        HolderGetter<Item> holderGetter3 = bootstrapContext.lookup(Registries.ITEM);

        register(bootstrapContext, LUNGE, Enchantment.enchantment(Enchantment.definition(holderGetter3.getOrThrow(Barched.ItemTags.LUNGE_ENCHANTABLE), 5, 3, Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(25, 8), 2, EquipmentSlotGroup.HAND)).withEffect(Barched.EnchantmentEffectComponents.POST_PIERCING_ATTACK, AllOf.entityEffects(new DamageItem(new LevelBasedValue.Constant(1.0F)), new ApplyExhaustion(LevelBasedValue.perLevel(4.0F)), new ApplyEntityImpulse(new Vec3(0.0D, 0.0D, 1.0D), new Vec3(1.0D, 0.0D, 1.0D), LevelBasedValue.perLevel(0.458F)), new PlaySoundEffect(Barched.SoundEvents.LUNGE, ConstantFloat.of(1.0F), ConstantFloat.of(1.0F))), AllOfCondition.allOf(InvertedLootItemCondition.invert(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity()))))));
    }
}
