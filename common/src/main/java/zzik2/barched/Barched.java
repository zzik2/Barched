package zzik2.barched;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.SpearMobsTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.CamelHusk;
import net.minecraft.world.entity.monster.Parched;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.PiercingWeapon;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import zzik2.barched.bridge.entity.AbstractHorseBridge;
import zzik2.zreflex.enumeration.ZEnumTool;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class Barched {

    public static final String MOD_ID = "barched";

    public static void init() {
        //why the hell field is not loaded?!@?!#?!@?!?#
        Object o = EnchantmentEffectComponents.POST_PIERCING_ATTACK;

        AutoConfig.register(BarchedConfig.class, GsonConfigSerializer::new);
    }

    public static BarchedConfig getConfig() {
        return AutoConfig.getConfigHolder(BarchedConfig.class).getConfig();
    }

    public static class SoundEvents {
        public static final SoundEvent PARCHED_AMBIENT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_AMBIENT");
        public static final SoundEvent PARCHED_HURT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_HURT");
        public static final SoundEvent PARCHED_DEATH = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_DEATH");
        public static final SoundEvent PARCHED_STEP = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_STEP");
        public static final SoundEvent PARROT_IMITATE_PARCHED = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARROT_IMITATE_PARCHED");
        public static final SoundEvent PARROT_IMITATE_CAMEL_HUSK = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARROT_IMITATE_CAMEL_HUSK");
        public static final SoundEvent CAMEL_HUSK_AMBIENT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_AMBIENT");
        public static final SoundEvent CAMEL_HUSK_DASH = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_DASH");
        public static final SoundEvent CAMEL_HUSK_DASH_READY = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_DASH_READY");
        public static final SoundEvent CAMEL_HUSK_DEATH = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_DEATH");
        public static final SoundEvent CAMEL_HUSK_EAT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_EAT");
        public static final SoundEvent CAMEL_HUSK_HURT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_HURT");
        public static final SoundEvent CAMEL_HUSK_SADDLE = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_SADDLE");
        public static final SoundEvent CAMEL_HUSK_SIT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_SIT");
        public static final SoundEvent CAMEL_HUSK_STAND = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_STAND");
        public static final SoundEvent CAMEL_HUSK_STEP = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_STEP");
        public static final SoundEvent CAMEL_HUSK_STEP_SAND = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_STEP_SAND");
        public static final SoundEvent ZOMBIE_HORSE_ANGRY = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "ZOMBIE_HORSE_ANGRY");
        public static final SoundEvent ZOMBIE_HORSE_EAT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "ZOMBIE_HORSE_EAT");
        public static final Holder<SoundEvent> LUNGE = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "LUNGE");
        public static final Holder<SoundEvent> LUNGE_1 = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "LUNGE_1");
        public static final Holder<SoundEvent> LUNGE_2 = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "LUNGE_2");
        public static final Holder<SoundEvent> LUNGE_3 = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "LUNGE_3");
        public static final Holder<SoundEvent> SPEAR_USE = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "SPEAR_USE");
        public static final Holder<SoundEvent> SPEAR_HIT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "SPEAR_HIT");
        public static final Holder<SoundEvent> SPEAR_ATTACK = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "SPEAR_ATTACK");
        public static final Holder<SoundEvent> SPEAR_WOOD_USE = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "SPEAR_WOOD_USE");
        public static final Holder<SoundEvent> SPEAR_WOOD_HIT = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "SPEAR_WOOD_HIT");
        public static final Holder<SoundEvent> SPEAR_WOOD_ATTACK = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "SPEAR_WOOD_ATTACK");


//        public static final SoundEvent TEMPLATE = ZReflectionTool.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "");
    }

    public static class EntityType {
        public static final net.minecraft.world.entity.EntityType<Parched> PARCHED = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.EntityType.class, "PARCHED");
        public static final net.minecraft.world.entity.EntityType<CamelHusk> CAMEL_HUSK = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.EntityType.class, "CAMEL_HUSK");
    }

    public static class MountPanicGoal extends PanicGoal {

        private final PathfinderMob mob;

        public MountPanicGoal(PathfinderMob pathfinderMob, double d) {
            super(pathfinderMob, d);
            this.mob = pathfinderMob;
        }

        public PathfinderMob getPathfinderMob() {
            return this.mob;
        }

        public boolean shouldPanic() {
            return !((AbstractHorseBridge) this.getPathfinderMob()).isMobControlled() && super.shouldPanic();
        }
    }

    public static class Items {
        public static final Item PARCHED_SPAWN_EGG = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "PARCHED_SPAWN_EGG");
        public static final Item CAMEL_HUSK_SPAWN_EGG = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "CAMEL_HUSK_SPAWN_EGG");
        public static final Item WOODEN_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "WOODEN_SPEAR");
        public static final Item STONE_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "STONE_SPEAR");
        public static final Item COPPER_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "COPPER_SPEAR");
        public static final Item IRON_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "IRON_SPEAR");
        public static final Item GOLDEN_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "GOLDEN_SPEAR");
        public static final Item DIAMOND_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "DIAMOND_SPEAR");
        public static final Item NETHERITE_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.Items.class, "NETHERITE_SPEAR");
    }

    public static class Enchantments {
        public static final ResourceKey<Enchantment> LUNGE = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.enchantment.Enchantments.class, "LUNGE");
    }

    public static class EnchantmentEffectComponents {

        public static final DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>> POST_PIERCING_ATTACK = ZReflectionTool.invokeStaticMethod(net.minecraft.world.item.enchantment.EnchantmentEffectComponents.class, "register", "post_piercing_attack", (UnaryOperator<DataComponentType.Builder<List<ConditionalEffect<EnchantmentEntityEffect>>>>) (builder) -> builder.persistent(ConditionalEffect.codec(EnchantmentEntityEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()));
    }

    public static class SmithingTemplateItem {
        public static final ResourceLocation EMPTY_SLOT_SPEAR = ZReflectionTool.getStaticFieldValue(net.minecraft.world.item.SmithingTemplateItem.class, "EMPTY_SLOT_SPEAR");
    }

    public static class Tiers {
        public static final net.minecraft.world.item.Tiers COPPER = ZEnumTool.addConstant(
                net.minecraft.world.item.Tiers.class,
                "COPPER",
                new Class<?>[] { TagKey.class, int.class, float.class, float.class, int.class, Supplier.class },
                Barched.BlockTags.INCORRECT_FOR_COPPER_TOOL, 190, 5.0F, 1.0F, 13,
                (Supplier<Ingredient>) () -> Ingredient.of(net.minecraft.world.item.Items.COPPER_INGOT)
        );
    }

    public static class ItemTags {
        public static final TagKey<Item> CAMEL_HUSK_FOOD = ZReflectionTool.getStaticFieldValue(net.minecraft.tags.ItemTags.class, "CAMEL_HUSK_FOOD");
        public static final TagKey<Item> ZOMBIE_HORSE_FOOD = ZReflectionTool.getStaticFieldValue(net.minecraft.tags.ItemTags.class, "ZOMBIE_HORSE_FOOD");
        public static final TagKey<Item> SPEARS = ZReflectionTool.getStaticFieldValue(net.minecraft.tags.ItemTags.class, "SPEARS");
        public static final TagKey<Item> LUNGE_ENCHANTABLE = ZReflectionTool.getStaticFieldValue(net.minecraft.tags.ItemTags.class, "LUNGE_ENCHANTABLE");
    }

    public static class BlockTags {
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = ZReflectionTool.getStaticFieldValue(net.minecraft.tags.BlockTags.class, "INCORRECT_FOR_COPPER_TOOL");
    }

    public static class HuskGroupData extends Zombie.ZombieGroupData {
        public boolean triedToSpawnCamelHusk = false;

        public HuskGroupData(Zombie.ZombieGroupData zombieGroupData) {
            super(zombieGroupData.isBaby, zombieGroupData.canSpawnJockey);
        }
    }

    public static class ServerboundPlayerActionPacket$Action {
        public static final ServerboundPlayerActionPacket.Action STAB = ZEnumTool.addConstant(ServerboundPlayerActionPacket.Action.class, "STAB");
    }

    public static class DataComponents {
        public static final DataComponentType<Float> MINIMUM_ATTACK_CHARGE = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "MINIMUM_ATTACK_CHARGE");

        public static final DataComponentType<PiercingWeapon> PIERCING_WEAPON = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "PIERCING_WEAPON");

        public static final DataComponentType<AttackRange> ATTACK_RANGE = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "ATTACK_RANGE");
    }

    public static class CriteriaTriggers {
        public static final SpearMobsTrigger SPEAR_MOBS_TRIGGER = ZReflectionTool.getStaticFieldValue(net.minecraft.advancements.CriteriaTriggers.class, "SPEAR_MOBS_TRIGGER");
    }

}
