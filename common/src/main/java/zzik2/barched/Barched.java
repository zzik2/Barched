package zzik2.barched;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function9;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.advancements.criterion.SpearMobsTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.SpearAttack;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.CamelHusk;
import net.minecraft.world.entity.monster.Parched;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.*;
import zzik2.barched.bridge.EnchantmentBridge;
import zzik2.barched.bridge.entity.AbstractHorseBridge;
import zzik2.barched.bridge.entity.EntityBridge;
import zzik2.barched.bridge.level.LevelBridge;
import zzik2.zreflex.enumeration.ZEnumTool;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.minecraft.core.Direction.NORTH;
import static net.minecraft.world.entity.Mob.checkMobSpawnRules;

public final class Barched {

    public static final String MOD_ID = "barched";

    public static void init() {
        //why the hell field is not loaded?!@?!#?!@?!?#
        Object o;
        o = EnchantmentEffectComponents.POST_PIERCING_ATTACK;
        o = Tiers.COPPER;
        o = UseAnim.BARCHED$SPEAR;
        o = ServerboundPlayerActionPacket$Action.STAB;

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

        public static DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>> POST_PIERCING_ATTACK;
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

    public static class UseAnim {
        public static final net.minecraft.world.item.UseAnim BARCHED$SPEAR = ZEnumTool.addConstant(
                net.minecraft.world.item.UseAnim.class,
                "BARCHED$SPEAR"
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
        public static final DataComponentType<UseEffects> USE_EFFECTS = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "USE_EFFECTS");

        public static final DataComponentType<EitherHolder<DamageType>> DAMAGE_TYPE = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "DAMAGE_TYPE");

        public static final DataComponentType<Float> MINIMUM_ATTACK_CHARGE = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "MINIMUM_ATTACK_CHARGE");

        public static final DataComponentType<PiercingWeapon> PIERCING_WEAPON = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "PIERCING_WEAPON");

        public static final DataComponentType<AttackRange> ATTACK_RANGE = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "ATTACK_RANGE");

        public static final DataComponentType<KineticWeapon> KINETIC_WEAPON = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "KINETIC_WEAPON");

        public static final DataComponentType<SwingAnimation> SWING_ANIMATION = ZReflectionTool.getStaticFieldValue(net.minecraft.core.component.DataComponents.class, "SWING_ANIMATION");
    }

    public static class CriteriaTriggers {
        public static final SpearMobsTrigger SPEAR_MOBS_TRIGGER = ZReflectionTool.getStaticFieldValue(net.minecraft.advancements.CriteriaTriggers.class, "SPEAR_MOBS_TRIGGER");
    }

    public static class StreamCodec {
        public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> net.minecraft.network.codec.StreamCodec<B, C> composite(net.minecraft.network.codec.StreamCodec<? super B, T1> streamCodec, Function<C, T1> function, net.minecraft.network.codec.StreamCodec<? super B, T2> streamCodec2, Function<C, T2> function2, net.minecraft.network.codec.StreamCodec<? super B, T3> streamCodec3, Function<C, T3> function3, net.minecraft.network.codec.StreamCodec<? super B, T4> streamCodec4, Function<C, T4> function4, net.minecraft.network.codec.StreamCodec<? super B, T5> streamCodec5, Function<C, T5> function5, net.minecraft.network.codec.StreamCodec<? super B, T6> streamCodec6, Function<C, T6> function6, net.minecraft.network.codec.StreamCodec<? super B, T7> streamCodec7, Function<C, T7> function7, net.minecraft.network.codec.StreamCodec<? super B, T8> streamCodec8, Function<C, T8> function8, net.minecraft.network.codec.StreamCodec<? super B, T9> streamCodec9, Function<C, T9> function9, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> function92) {
            return new net.minecraft.network.codec.StreamCodec<B, C>() {
                public C decode(B object) {
                    T1 object2 = streamCodec.decode(object);
                    T2 object3 = streamCodec2.decode(object);
                    T3 object4 = streamCodec3.decode(object);
                    T4 object5 = streamCodec4.decode(object);
                    T5 object6 = streamCodec5.decode(object);
                    T6 object7 = streamCodec6.decode(object);
                    T7 object8 = streamCodec7.decode(object);
                    T8 object9 = streamCodec8.decode(object);
                    T9 object10 = streamCodec9.decode(object);
                    return function92.apply(object2, object3, object4, object5, object6, object7, object8, object9, object10);
                }

                public void encode(B object, C object2) {
                    streamCodec.encode(object, function.apply(object2));
                    streamCodec2.encode(object, function2.apply(object2));
                    streamCodec3.encode(object, function3.apply(object2));
                    streamCodec4.encode(object, function4.apply(object2));
                    streamCodec5.encode(object, function5.apply(object2));
                    streamCodec6.encode(object, function6.apply(object2));
                    streamCodec7.encode(object, function7.apply(object2));
                    streamCodec8.encode(object, function8.apply(object2));
                    streamCodec9.encode(object, function9.apply(object2));
                }
            };
        }
    }

    public static class DamageTypes {
        public static final ResourceKey<DamageType> SPEAR = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.withDefaultNamespace("spear"));
        public static final ResourceKey<DamageType> MACE_SMASH = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.withDefaultNamespace("mace_smash"));
    }

    public static class MemoryModuleType {
        public static final net.minecraft.world.entity.ai.memory.MemoryModuleType<Integer> SPEAR_FLEEING_TIME = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.ai.memory.MemoryModuleType.class, "SPEAR_FLEEING_TIME");
        public static final net.minecraft.world.entity.ai.memory.MemoryModuleType<Vec3> SPEAR_FLEEING_POSITION = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.ai.memory.MemoryModuleType.class, "SPEAR_FLEEING_POSITION");
        public static final net.minecraft.world.entity.ai.memory.MemoryModuleType<Vec3> SPEAR_CHARGE_POSITION = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.ai.memory.MemoryModuleType.class, "SPEAR_CHARGE_POSITION");
        public static final net.minecraft.world.entity.ai.memory.MemoryModuleType<Integer> SPEAR_ENGAGE_TIME = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.ai.memory.MemoryModuleType.class, "SPEAR_ENGAGE_TIME");
        public static final net.minecraft.world.entity.ai.memory.MemoryModuleType<SpearAttack.SpearStatus> SPEAR_STATUS = ZReflectionTool.getStaticFieldValue(net.minecraft.world.entity.ai.memory.MemoryModuleType.class, "SPEAR_STATUS");
    }

    public static class EnchantmentHelper {

        public static void doLungeEffects(ServerLevel serverLevel, Entity entity) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                Barched.EnchantmentHelper.runIterationOnItem(entity.getWeaponItem(), EquipmentSlot.MAINHAND, livingEntity, (holder, i, enchantedItemInUse) -> {
                    ((EnchantmentBridge) (Object) holder.value()).doLunge(serverLevel, i, enchantedItemInUse, entity);
                });
            }
        }

        public static void runIterationOnItem(ItemStack itemStack, EnchantmentHelper.EnchantmentVisitor enchantmentVisitor) {
            ItemEnchantments itemEnchantments = (ItemEnchantments)itemStack.getOrDefault(net.minecraft.core.component.DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            Iterator var3 = itemEnchantments.entrySet().iterator();

            while(var3.hasNext()) {
                Object2IntMap.Entry<Holder<Enchantment>> entry = (Object2IntMap.Entry)var3.next();
                enchantmentVisitor.accept((Holder)entry.getKey(), entry.getIntValue());
            }

        }

        public static void runIterationOnItem(ItemStack itemStack, EquipmentSlot equipmentSlot, LivingEntity livingEntity, EnchantmentHelper.EnchantmentInSlotVisitor enchantmentInSlotVisitor) {
            if (!itemStack.isEmpty()) {
                ItemEnchantments itemEnchantments = (ItemEnchantments)itemStack.get(net.minecraft.core.component.DataComponents.ENCHANTMENTS);
                if (itemEnchantments != null && !itemEnchantments.isEmpty()) {
                    EnchantedItemInUse enchantedItemInUse = new EnchantedItemInUse(itemStack, equipmentSlot, livingEntity);
                    Iterator var6 = itemEnchantments.entrySet().iterator();

                    while(var6.hasNext()) {
                        Object2IntMap.Entry<Holder<Enchantment>> entry = (Object2IntMap.Entry)var6.next();
                        Holder<Enchantment> holder = (Holder)entry.getKey();
                        if (((Enchantment)holder.value()).matchingSlot(equipmentSlot)) {
                            enchantmentInSlotVisitor.accept(holder, entry.getIntValue(), enchantedItemInUse);
                        }
                    }

                }
            }
        }

        @FunctionalInterface
        public interface EnchantmentVisitor {
            void accept(Holder<Enchantment> holder, int i);
        }

        @FunctionalInterface
        public interface EnchantmentInSlotVisitor {
            void accept(Holder<Enchantment> holder, int i, EnchantedItemInUse enchantedItemInUse);
        }
    }

    public static class ProjectileUtil {

        public static Either<BlockHitResult, Collection<EntityHitResult>> getHitEntitiesAlong(Entity entity, AttackRange attackRange, Predicate<Entity> predicate, ClipContext.Block block) {
            Vec3 vec3 = ((EntityBridge) entity).getHeadLookAngle();
            Vec3 vec32 = entity.getEyePosition();
            Vec3 vec33 = vec32.add(vec3.scale((double)attackRange.effectiveMinRange(entity)));
            double d = entity.getKnownMovement().dot(vec3);
            Vec3 vec34 = vec32.add(vec3.scale((double)attackRange.effectiveMaxRange(entity) + Math.max(0.0D, d)));
            return getHitEntitiesAlong(entity, vec32, vec33, predicate, vec34, attackRange.hitboxMargin(), block);
        }

        public static Either<BlockHitResult, Collection<EntityHitResult>> getHitEntitiesAlong(Entity entity, Vec3 vec3, Vec3 vec32, Predicate<Entity> predicate, Vec3 vec33, float f, ClipContext.Block block) {
            Level level = entity.level();
            BlockHitResult blockHitResult = ((LevelBridge) level).clipIncludingBorder(new ClipContext(vec3, vec33, block, ClipContext.Fluid.NONE, entity));
            if (blockHitResult.getType() != HitResult.Type.MISS) {
                vec33 = blockHitResult.getLocation();
                if (vec3.distanceToSqr(vec33) < vec3.distanceToSqr(vec32)) {
                    return Either.left(blockHitResult);
                }
            }

            AABB aABB = AABB.ofSize(vec32, (double)f, (double)f, (double)f).expandTowards(vec33.subtract(vec32)).inflate(1.0D);
            Collection<EntityHitResult> collection = getManyEntityHitResult(level, entity, vec32, vec33, aABB, predicate, f, block, true);
            return !collection.isEmpty() ? Either.right(collection) : Either.left(blockHitResult);
        }

        public static Collection<EntityHitResult> getManyEntityHitResult(Level level, Entity entity, Vec3 vec3, Vec3 vec32, AABB aABB, Predicate<Entity> predicate, float f, ClipContext.Block block, boolean bl) {
            List<EntityHitResult> list = new ArrayList();
            Iterator var10 = level.getEntities(entity, aABB, predicate).iterator();

            while(true) {
                while(var10.hasNext()) {
                    Entity entity2 = (Entity)var10.next();
                    AABB aABB2 = entity2.getBoundingBox();
                    if (bl && aABB2.contains(vec3)) {
                        list.add(new EntityHitResult(entity2, vec3));
                    } else {
                        Optional<Vec3> optional = aABB2.clip(vec3, vec32);
                        if (optional.isPresent()) {
                            list.add(new EntityHitResult(entity2, (Vec3)optional.get()));
                        } else if (!((double)f <= 0.0D)) {
                            Optional<Vec3> optional2 = aABB2.inflate((double)f).clip(vec3, vec32);
                            if (!optional2.isEmpty()) {
                                Vec3 vec33 = (Vec3)optional2.get();
                                Vec3 vec34 = aABB2.getCenter();
                                BlockHitResult blockHitResult = ((LevelBridge) level).clipIncludingBorder(new ClipContext(vec33, vec34, block, ClipContext.Fluid.NONE, entity));
                                if (blockHitResult.getType() != HitResult.Type.MISS) {
                                    vec34 = blockHitResult.getLocation();
                                }

                                Optional<Vec3> optional3 = entity2.getBoundingBox().clip(vec33, vec34);
                                if (optional3.isPresent()) {
                                    list.add(new EntityHitResult(entity2, (Vec3)optional3.get()));
                                }
                            }
                        }
                    }
                }

                return list;
            }
        }
    }

    public static class Direction {

        public static final net.minecraft.core.Direction[] VALUES = net.minecraft.core.Direction.values();

        public static net.minecraft.core.Direction getApproximateNearest(double d, double e, double f) {
            return getApproximateNearest((float)d, (float)e, (float)f);
        }

        public static net.minecraft.core.Direction getApproximateNearest(float f, float g, float h) {
            net.minecraft.core.Direction direction = NORTH;
            float i = Float.MIN_VALUE;
            net.minecraft.core.Direction[] var5 = VALUES;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                net.minecraft.core.Direction direction2 = var5[var7];
                float j = f * (float)direction2.getNormal().getX() + g * (float)direction2.getNormal().getY() + h * (float)direction2.getNormal().getZ();
                if (j > i) {
                    i = j;
                    direction = direction2;
                }
            }

            return direction;
        }

        public static net.minecraft.core.Direction getApproximateNearest(Vec3 vec3) {
            return getApproximateNearest(vec3.x, vec3.y, vec3.z);
        }
    }

    public static class Monster {
        public static boolean checkMonsterSpawnRules0(net.minecraft.world.entity.EntityType<? extends Mob> arg, ServerLevelAccessor arg2, MobSpawnType arg3, BlockPos arg4, RandomSource arg5) {
            return arg2.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(arg3) || net.minecraft.world.entity.monster.Monster.isDarkEnoughToSpawn(arg2, arg4, arg5)) && checkMobSpawnRules(arg, arg2, arg3, arg4, arg5);
        }

        public static boolean checkSurfaceMonstersSpawnRules(net.minecraft.world.entity.EntityType<? extends Mob> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
            return checkMonsterSpawnRules(entityType, serverLevelAccessor, entitySpawnReason, blockPos, randomSource) && (MobSpawnType.isSpawner(entitySpawnReason) || serverLevelAccessor.canSeeSky(blockPos));
        }

        public static boolean checkMonsterSpawnRules(net.minecraft.world.entity.EntityType<? extends Mob> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
            return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(entitySpawnReason) || net.minecraft.world.entity.monster.Monster.isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource)) && checkMobSpawnRules(entityType, serverLevelAccessor, entitySpawnReason, blockPos, randomSource);
        }
    }

    public static class ExtraCodecs {

        public static Codec<Float> floatRange(float f, float g) {
            return floatRangeMinExclusiveWithMessage(f, g, (float_) -> {
                return "Value must be within range [" + f + ";" + g + "]: " + float_;
            });
        }

        private static Codec<Float> floatRangeMinExclusiveWithMessage(float f, float g, Function<Float, String> function) {
            return Codec.FLOAT.validate((float_) -> float_.compareTo(f) > 0 && float_.compareTo(g) <= 0 ? DataResult.success(float_) : DataResult.error(() -> (String)function.apply(float_)));
        }
    }
}
