package net.minecraft.world.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.bridge.level.LevelBridge;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public record PiercingWeapon(boolean dealsKnockback, boolean dismounts, Optional<Holder<SoundEvent>> sound, Optional<Holder<SoundEvent>> hitSound) {
   public static final Codec<PiercingWeapon> CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.BOOL.optionalFieldOf("deals_knockback", true).forGetter(PiercingWeapon::dealsKnockback), Codec.BOOL.optionalFieldOf("dismounts", false).forGetter(PiercingWeapon::dismounts), SoundEvent.CODEC.optionalFieldOf("sound").forGetter(PiercingWeapon::sound), SoundEvent.CODEC.optionalFieldOf("hit_sound").forGetter(PiercingWeapon::hitSound)).apply(instance, PiercingWeapon::new);
   });
   public static final StreamCodec<RegistryFriendlyByteBuf, PiercingWeapon> STREAM_CODEC;

   public PiercingWeapon(boolean dealsKnockback, boolean dismounts, Optional<Holder<SoundEvent>> sound, Optional<Holder<SoundEvent>> hitSound) {
      this.dealsKnockback = dealsKnockback;
      this.dismounts = dismounts;
      this.sound = sound;
      this.hitSound = hitSound;
   }

   public void makeSound(Entity entity) {
      this.sound.ifPresent((holder) -> {
         ((LevelBridge) entity.level()).playSound(entity, entity.getX(), entity.getY(), entity.getZ(), holder, entity.getSoundSource(), 1.0F, 1.0F);
      });
   }

   public void makeHitSound(Entity entity) {
      this.hitSound.ifPresent((holder) -> {
         ((LevelBridge) entity.level()).playSound((Entity)null, entity.getX(), entity.getY(), entity.getZ(), (Holder)holder, entity.getSoundSource(), 1.0F, 1.0F);
      });
   }

   public static boolean canHitEntity(Entity entity, Entity entity2) {
      if (!entity2.isInvulnerable() && entity2.isAlive()) {
         if (entity2 instanceof Interaction) {
            return true;
         } else if (!entity2.canBeHitByProjectile()) {
            return false;
         } else {
            if (entity2 instanceof Player) {
               Player player = (Player)entity2;
               if (entity instanceof Player) {
                  Player player2 = (Player)entity;
                  if (!player2.canHarmPlayer(player)) {
                     return false;
                  }
               }
            }

            return !entity.isPassengerOfSameVehicle(entity2);
         }
      } else {
         return false;
      }
   }

   public void attack(LivingEntity livingEntity, EquipmentSlot equipmentSlot) {
      float f = (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
      AttackRange attackRange = ((LivingEntityBridge) livingEntity).entityAttackRange();
      boolean bl = false;

      EntityHitResult entityHitResult;
      for(Iterator var6 = ((Collection) Barched.ProjectileUtil.getHitEntitiesAlong(livingEntity, attackRange, (entity) -> {
         return canHitEntity(livingEntity, entity);
      }, ClipContext.Block.COLLIDER).map((blockHitResult) -> {
         return List.of();
      }, (collection) -> {
         return collection;
      })).iterator(); var6.hasNext(); bl |= ((LivingEntityBridge) livingEntity).stabAttack(equipmentSlot, entityHitResult.getEntity(), f, true, this.dealsKnockback, this.dismounts)) {
         entityHitResult = (EntityHitResult)var6.next();
      }

      ((LivingEntityBridge) livingEntity).onAttack();
      ((LivingEntityBridge) livingEntity).lungeForwardMaybe();
      if (bl) {
         this.makeHitSound(livingEntity);
      }

      this.makeSound(livingEntity);
      livingEntity.swing(InteractionHand.MAIN_HAND, false);
   }

   public boolean dealsKnockback() {
      return this.dealsKnockback;
   }

   public boolean dismounts() {
      return this.dismounts;
   }

   public Optional<Holder<SoundEvent>> sound() {
      return this.sound;
   }

   public Optional<Holder<SoundEvent>> hitSound() {
      return this.hitSound;
   }

   static {
      STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, PiercingWeapon::dealsKnockback, ByteBufCodecs.BOOL, PiercingWeapon::dismounts, SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), PiercingWeapon::sound, SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), PiercingWeapon::hitSound, PiercingWeapon::new);
   }
}
