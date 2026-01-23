package zzik2.barched;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.CamelHusk;
import net.minecraft.world.entity.monster.Parched;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import zzik2.barched.bridge.AbstractHorseBridge;
import zzik2.zreflex.enumeration.ZEnumTool;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.function.Supplier;

public final class Barched {

    public static final String MOD_ID = "barched";

    public static void init() {
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

}
