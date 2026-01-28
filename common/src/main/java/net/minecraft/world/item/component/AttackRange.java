package net.minecraft.world.item.component;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.EntityBridge;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public record AttackRange(float minRange, float maxRange, float minCreativeRange, float maxCreativeRange, float hitboxMargin, float mobFactor) {

   private static final Codec<Float> FLOAT_RANGE = Barched.ExtraCodecs.floatRange(0.0F, 64.0F);
   private static final Codec<Float> FLOAT_RANGE_1 = Barched.ExtraCodecs.floatRange(0.0F, 1.0F);

   public static final Codec<AttackRange> CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(FLOAT_RANGE.optionalFieldOf("min_reach", 0.0F).forGetter(AttackRange::minRange), FLOAT_RANGE.optionalFieldOf("max_reach", 3.0F).forGetter(AttackRange::maxRange), FLOAT_RANGE.optionalFieldOf("min_creative_reach", 0.0F).forGetter(AttackRange::minCreativeRange), FLOAT_RANGE.optionalFieldOf("max_creative_reach", 5.0F).forGetter(AttackRange::maxCreativeRange), FLOAT_RANGE_1.optionalFieldOf("hitbox_margin", 0.3F).forGetter(AttackRange::hitboxMargin), Codec.floatRange(0.0F, 2.0F).optionalFieldOf("mob_factor", 1.0F).forGetter(AttackRange::mobFactor)).apply(instance, AttackRange::new);
   });
   public static final StreamCodec<ByteBuf, AttackRange> STREAM_CODEC;

   public AttackRange(float minRange, float maxRange, float minCreativeRange, float maxCreativeRange, float hitboxMargin, float mobFactor) {
      this.minRange = minRange;
      this.maxRange = maxRange;
      this.minCreativeRange = minCreativeRange;
      this.maxCreativeRange = maxCreativeRange;
      this.hitboxMargin = hitboxMargin;
      this.mobFactor = mobFactor;
   }

   public static AttackRange defaultFor(LivingEntity livingEntity) {
      return new AttackRange(0.0F, (float)livingEntity.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE), 0.0F, (float)livingEntity.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE), 0.0F, 1.0F);
   }

   public HitResult getClosesetHit(Entity entity, float f, Predicate<Entity> predicate) {
      Either<BlockHitResult, Collection<EntityHitResult>> either = Barched.ProjectileUtil.getHitEntitiesAlong(entity, this, predicate, ClipContext.Block.OUTLINE);
      if (either.left().isPresent()) {
         return (HitResult)either.left().get();
      } else {
         Collection<EntityHitResult> collection = (Collection)either.right().get();
         EntityHitResult entityHitResult = null;
         Vec3 vec3 = entity.getEyePosition(f);
         double d = Double.MAX_VALUE;
         Iterator var10 = collection.iterator();

         while(var10.hasNext()) {
            EntityHitResult entityHitResult2 = (EntityHitResult)var10.next();
            double e = vec3.distanceToSqr(entityHitResult2.getLocation());
            if (e < d) {
               d = e;
               entityHitResult = entityHitResult2;
            }
         }

         if (entityHitResult != null) {
            return entityHitResult;
         } else {
            Vec3 vec32 = ((EntityBridge) entity).getHeadLookAngle();
            Vec3 vec33 = entity.getEyePosition(f).add(vec32);
            return BlockHitResult.miss(vec33, Barched.Direction.getApproximateNearest(vec32), BlockPos.containing(vec33));
         }
      }
   }

   public float effectiveMinRange(Entity entity) {
      if (entity instanceof Player) {
         Player player = (Player)entity;
         if (player.isSpectator()) {
            return 0.0F;
         } else {
            return player.isCreative() ? this.minCreativeRange : this.minRange;
         }
      } else {
         return this.minRange * this.mobFactor;
      }
   }

   public float effectiveMaxRange(Entity entity) {
      if (entity instanceof Player) {
         Player player = (Player)entity;
         return player.isCreative() ? this.maxCreativeRange : this.maxRange;
      } else {
         return this.maxRange * this.mobFactor;
      }
   }

   public boolean isInRange(LivingEntity livingEntity, Vec3 vec3) {
      Objects.requireNonNull(vec3);
      return this.isInRange(livingEntity, vec3::distanceToSqr, 0.0D);
   }

   public boolean isInRange(LivingEntity livingEntity, AABB aABB, double d) {
      Objects.requireNonNull(aABB);
      return this.isInRange(livingEntity, aABB::distanceToSqr, d);
   }

   private boolean isInRange(LivingEntity livingEntity, ToDoubleFunction<Vec3> toDoubleFunction, double d) {
      double e = Math.sqrt(toDoubleFunction.applyAsDouble(livingEntity.getEyePosition()));
      double f = (double)(this.effectiveMinRange(livingEntity) - this.hitboxMargin) - d;
      double g = (double)(this.effectiveMaxRange(livingEntity) + this.hitboxMargin) + d;
      return e >= f && e <= g;
   }

   public float minRange() {
      return this.minRange;
   }

   public float maxRange() {
      return this.maxRange;
   }

   public float minCreativeRange() {
      return this.minCreativeRange;
   }

   public float maxCreativeRange() {
      return this.maxCreativeRange;
   }

   public float hitboxMargin() {
      return this.hitboxMargin;
   }

   public float mobFactor() {
      return this.mobFactor;
   }

   static {
      STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, AttackRange::minRange, ByteBufCodecs.FLOAT, AttackRange::maxRange, ByteBufCodecs.FLOAT, AttackRange::minCreativeRange, ByteBufCodecs.FLOAT, AttackRange::maxCreativeRange, ByteBufCodecs.FLOAT, AttackRange::hitboxMargin, ByteBufCodecs.FLOAT, AttackRange::mobFactor, AttackRange::new);
   }
}