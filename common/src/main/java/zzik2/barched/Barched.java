package zzik2.barched;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.CamelHusk;
import net.minecraft.world.entity.monster.Parched;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import zzik2.barched.reflection.ReflectionUtils;

public final class Barched {

    public static final String MOD_ID = "barched";

    public static void init() {
    }

    public static class SoundEvents {
        public static final SoundEvent PARCHED_AMBIENT = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_AMBIENT");
        public static final SoundEvent PARCHED_HURT = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_HURT");
        public static final SoundEvent PARCHED_DEATH = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_DEATH");
        public static final SoundEvent PARCHED_STEP = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARCHED_STEP");
        public static final SoundEvent PARROT_IMITATE_PARCHED = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARROT_IMITATE_PARCHED");
        public static final SoundEvent PARROT_IMITATE_CAMEL_HUSK = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "PARROT_IMITATE_CAMEL_HUSK");
        public static final SoundEvent CAMEL_HUSK_AMBIENT = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_AMBIENT");
        public static final SoundEvent CAMEL_HUSK_DASH = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_DASH");
        public static final SoundEvent CAMEL_HUSK_DASH_READY = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_DASH_READY");
        public static final SoundEvent CAMEL_HUSK_DEATH = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_DEATH");
        public static final SoundEvent CAMEL_HUSK_EAT = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_EAT");
        public static final SoundEvent CAMEL_HUSK_HURT = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_HURT");
        public static final Holder.Reference<SoundEvent> CAMEL_HUSK_SADDLE = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_SADDLE");
        public static final SoundEvent CAMEL_HUSK_SIT = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_SIT");
        public static final SoundEvent CAMEL_HUSK_STAND = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_STAND");
        public static final SoundEvent CAMEL_HUSK_STEP = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_STEP");
        public static final SoundEvent CAMEL_HUSK_STEP_SAND = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "CAMEL_HUSK_STEP_SAND");


//        public static final SoundEvent TEMPLATE = ReflectionUtils.getStaticFieldValue(net.minecraft.sounds.SoundEvents.class, "");
    }

    public static class EntityType {
        public static final net.minecraft.world.entity.EntityType<Parched> PARCHED = ReflectionUtils.getStaticFieldValue(net.minecraft.world.entity.EntityType.class, "PARCHED");
        public static final net.minecraft.world.entity.EntityType<CamelHusk> CAMEL_HUSK = ReflectionUtils.getStaticFieldValue(net.minecraft.world.entity.EntityType.class, "CAMEL_HUSK");
    }

    public static class Items {
        public static final Item PARCHED_SPAWN_EGG = ReflectionUtils.getStaticFieldValue(net.minecraft.world.item.Items.class, "PARCHED_SPAWN_EGG");
        public static final Item CAMEL_HUSK_SPAWN_EGG = ReflectionUtils.getStaticFieldValue(net.minecraft.world.item.Items.class, "CAMEL_HUSK_SPAWN_EGG");
    }

    public static class ItemTags {
        public static final TagKey<Item> CAMEL_HUSK_FOOD = ReflectionUtils.getStaticFieldValue(net.minecraft.tags.ItemTags.class, "CAMEL_HUSK_FOOD");
    }

    public static class HuskGroupData extends Zombie.ZombieGroupData {
        public boolean triedToSpawnCamelHusk = false;

        public HuskGroupData(Zombie.ZombieGroupData zombieGroupData) {
            super(zombieGroupData.isBaby, zombieGroupData.canSpawnJockey);
        }
    }
}
