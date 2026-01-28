package net.minecraft.world.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.EntityBridge;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.level.LevelBridge;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public record KineticWeapon(int contactCooldownTicks, int delayTicks, Optional<Condition> dismountConditions, Optional<KineticWeapon.Condition> knockbackConditions, Optional<KineticWeapon.Condition> damageConditions, float forwardMovement, float damageMultiplier, Optional<Holder<SoundEvent>> sound, Optional<Holder<SoundEvent>> hitSound) {
   
   public static final int HIT_FEEDBACK_TICKS = 10;

   public static final Codec<KineticWeapon> CODEC = RecordCodecBuilder.create((instance) -> instance.group(ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("contact_cooldown_ticks", 10).forGetter(KineticWeapon::contactCooldownTicks), ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("delay_ticks", 0).forGetter(KineticWeapon::delayTicks), KineticWeapon.Condition.CODEC.optionalFieldOf("dismount_conditions").forGetter(KineticWeapon::dismountConditions), KineticWeapon.Condition.CODEC.optionalFieldOf("knockback_conditions").forGetter(KineticWeapon::knockbackConditions), KineticWeapon.Condition.CODEC.optionalFieldOf("damage_conditions").forGetter(KineticWeapon::damageConditions), Codec.FLOAT.optionalFieldOf("forward_movement", 0.0F).forGetter(KineticWeapon::forwardMovement), Codec.FLOAT.optionalFieldOf("damage_multiplier", 1.0F).forGetter(KineticWeapon::damageMultiplier), SoundEvent.CODEC.optionalFieldOf("sound").forGetter(KineticWeapon::sound), SoundEvent.CODEC.optionalFieldOf("hit_sound").forGetter(KineticWeapon::hitSound)).apply(instance, KineticWeapon::new)
   );

   public static final StreamCodec<RegistryFriendlyByteBuf, KineticWeapon> STREAM_CODEC;

   public KineticWeapon(int contactCooldownTicks, int delayTicks, Optional<KineticWeapon.Condition> dismountConditions, Optional<KineticWeapon.Condition> knockbackConditions, Optional<KineticWeapon.Condition> damageConditions, float forwardMovement, float damageMultiplier, Optional<Holder<SoundEvent>> sound, Optional<Holder<SoundEvent>> hitSound) {
      this.contactCooldownTicks = contactCooldownTicks;
      this.delayTicks = delayTicks;
      this.dismountConditions = dismountConditions;
      this.knockbackConditions = knockbackConditions;
      this.damageConditions = damageConditions;
      this.forwardMovement = forwardMovement;
      this.damageMultiplier = damageMultiplier;
      this.sound = sound;
      this.hitSound = hitSound;
   }

   public static Vec3 getMotion(Entity entity) {
      if (!(entity instanceof Player) && entity.isPassenger()) {
         entity = entity.getRootVehicle();
      }

      return ((EntityBridge) entity).getKnownSpeed().scale(20.0D);
   }

   public void makeSound(Entity entity) {
      this.sound.ifPresent((holder) -> {
         ((LevelBridge) entity.level()).playSound(entity, entity.getX(), entity.getY(), entity.getZ(), holder, entity.getSoundSource(), 1.0F, 1.0F);
      });
   }

   public void makeLocalHitSound(Entity entity) {
      this.hitSound.ifPresent((holder) -> {
         entity.level().playLocalSound(entity, (SoundEvent)holder.value(), entity.getSoundSource(), 1.0F, 1.0F);
      });
   }

   public int computeDamageUseDuration() {
      return this.delayTicks + (Integer)this.damageConditions.map(KineticWeapon.Condition::maxDurationTicks).orElse(0);
   }

   public void damageEntities(ItemStack itemStack, int i, LivingEntity livingEntity, EquipmentSlot equipmentSlot) {
      int j = itemStack.getUseDuration(livingEntity) - i;
      if (j >= this.delayTicks) {
         j -= this.delayTicks;
         Vec3 vec3 = livingEntity.getLookAngle();
         double d = vec3.dot(getMotion(livingEntity));
         float f = livingEntity instanceof Player ? 1.0F : 0.2F;
         AttackRange attackRange = ((LivingEntityBridge) livingEntity).entityAttackRange();
         double e = livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
         boolean bl = false;
         Iterator var14 = ((Collection) Barched.ProjectileUtil.getHitEntitiesAlong(livingEntity, attackRange, (entityx) -> {
            return PiercingWeapon.canHitEntity(livingEntity, entityx);
         }, ClipContext.Block.COLLIDER).map((blockHitResult) -> {
            return List.of();
         }, (collection) -> {
            return collection;
         })).iterator();

         while(true) {
            Object entity;
            double h;
            boolean bl3;
            boolean bl4;
            boolean bl5;
            do {
               boolean bl2;
               do {
                  if (!var14.hasNext()) {
                     if (bl) {
                        livingEntity.level().broadcastEntityEvent(livingEntity, (byte)2);
                        if (livingEntity instanceof ServerPlayer) {
                           ServerPlayer serverPlayer = (ServerPlayer)livingEntity;
                           Barched.CriteriaTriggers.SPEAR_MOBS_TRIGGER.trigger(serverPlayer, ((LivingEntityBridge) livingEntity).stabbedEntities((entityx) -> {
                              return entityx instanceof LivingEntity;
                           }));
                        }
                     }

                     return;
                  }

                  EntityHitResult entityHitResult = (EntityHitResult)var14.next();
                  entity = entityHitResult.getEntity();
                  if (entity instanceof EnderDragonPart) {
                     EnderDragonPart enderDragonPart = (EnderDragonPart)entity;
                     entity = enderDragonPart.parentMob;
                  }

                  bl2 = ((LivingEntityBridge) livingEntity).wasRecentlyStabbed((Entity)entity, this.contactCooldownTicks);
               } while(bl2);

               ((LivingEntityBridge) livingEntity).rememberStabbedEntity((Entity)entity);
               double g = vec3.dot(getMotion((Entity)entity));
               h = Math.max(0.0D, d - g);
               bl3 = this.dismountConditions.isPresent() && ((KineticWeapon.Condition)this.dismountConditions.get()).test(j, d, h, (double)f);
               bl4 = this.knockbackConditions.isPresent() && ((KineticWeapon.Condition)this.knockbackConditions.get()).test(j, d, h, (double)f);
               bl5 = this.damageConditions.isPresent() && ((KineticWeapon.Condition)this.damageConditions.get()).test(j, d, h, (double)f);
            } while(!bl3 && !bl4 && !bl5);

            float k = (float)e + (float) Mth.floor(h * (double)this.damageMultiplier);
            bl |= ((LivingEntityBridge) livingEntity).stabAttack(equipmentSlot, (Entity)entity, k, bl5, bl4, bl3);
         }
      }
   }

   public int contactCooldownTicks() {
      return this.contactCooldownTicks;
   }

   public int delayTicks() {
      return this.delayTicks;
   }

   public Optional<KineticWeapon.Condition> dismountConditions() {
      return this.dismountConditions;
   }

   public Optional<KineticWeapon.Condition> knockbackConditions() {
      return this.knockbackConditions;
   }

   public Optional<KineticWeapon.Condition> damageConditions() {
      return this.damageConditions;
   }

   public float forwardMovement() {
      return this.forwardMovement;
   }

   public float damageMultiplier() {
      return this.damageMultiplier;
   }

   public Optional<Holder<SoundEvent>> sound() {
      return this.sound;
   }

   public Optional<Holder<SoundEvent>> hitSound() {
      return this.hitSound;
   }

   static {
      STREAM_CODEC = Barched.StreamCodec.composite(ByteBufCodecs.VAR_INT, KineticWeapon::contactCooldownTicks, ByteBufCodecs.VAR_INT, KineticWeapon::delayTicks, KineticWeapon.Condition.STREAM_CODEC.apply(ByteBufCodecs::optional), KineticWeapon::dismountConditions, KineticWeapon.Condition.STREAM_CODEC.apply(ByteBufCodecs::optional), KineticWeapon::knockbackConditions, KineticWeapon.Condition.STREAM_CODEC.apply(ByteBufCodecs::optional), KineticWeapon::damageConditions, ByteBufCodecs.FLOAT, KineticWeapon::forwardMovement, ByteBufCodecs.FLOAT, KineticWeapon::damageMultiplier, SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), KineticWeapon::sound, SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), KineticWeapon::hitSound, KineticWeapon::new);
   }

   public static record Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
      public static final Codec<KineticWeapon.Condition> CODEC = RecordCodecBuilder.create((instance) -> {
         return instance.group(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("max_duration_ticks").forGetter(KineticWeapon.Condition::maxDurationTicks), Codec.FLOAT.optionalFieldOf("min_speed", 0.0F).forGetter(KineticWeapon.Condition::minSpeed), Codec.FLOAT.optionalFieldOf("min_relative_speed", 0.0F).forGetter(KineticWeapon.Condition::minRelativeSpeed)).apply(instance, KineticWeapon.Condition::new);
      });
      public static final StreamCodec<ByteBuf, KineticWeapon.Condition> STREAM_CODEC;

      public Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
         this.maxDurationTicks = maxDurationTicks;
         this.minSpeed = minSpeed;
         this.minRelativeSpeed = minRelativeSpeed;
      }

      public boolean test(int i, double d, double e, double f) {
         return i <= this.maxDurationTicks && d >= (double)this.minSpeed * f && e >= (double)this.minRelativeSpeed * f;
      }

      public static Optional<KineticWeapon.Condition> ofAttackerSpeed(int i, float f) {
         return Optional.of(new KineticWeapon.Condition(i, f, 0.0F));
      }

      public static Optional<KineticWeapon.Condition> ofRelativeSpeed(int i, float f) {
         return Optional.of(new KineticWeapon.Condition(i, 0.0F, f));
      }

      public int maxDurationTicks() {
         return this.maxDurationTicks;
      }

      public float minSpeed() {
         return this.minSpeed;
      }

      public float minRelativeSpeed() {
         return this.minRelativeSpeed;
      }

      static {
         STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, KineticWeapon.Condition::maxDurationTicks, ByteBufCodecs.FLOAT, KineticWeapon.Condition::minSpeed, ByteBufCodecs.FLOAT, KineticWeapon.Condition::minRelativeSpeed, KineticWeapon.Condition::new);
      }
   }
}