package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.component.KineticWeapon;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.MobBridge;
import zzik2.barched.bridge.entity.ai.LandRandomPosBridge;

import java.util.EnumSet;
import java.util.Optional;

public class SpearUseGoal<T extends Monster> extends Goal implements LandRandomPosBridge {
   static final int MIN_REPOSITION_DISTANCE = 6;
   static final int MAX_REPOSITION_DISTANCE = 7;
   static final int MIN_COOLDOWN_DISTANCE = 9;
   static final int MAX_COOLDOWN_DISTANCE = 11;
   static final double MAX_FLEEING_TIME = (double)reducedTickDelay(100);
   private final T mob;
   @Nullable
   private SpearUseGoal.SpearUseState state;
   double speedModifierWhenCharging;
   double speedModifierWhenRepositioning;
   float approachDistanceSq;
   float targetInRangeRadiusSq;

   public SpearUseGoal(T monster, double d, double e, float f, float g) {
      this.mob = monster;
      this.speedModifierWhenCharging = d;
      this.speedModifierWhenRepositioning = e;
      this.approachDistanceSq = f * f;
      this.targetInRangeRadiusSq = g * g;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean canUse() {
      return this.ableToAttack() && !this.mob.isUsingItem();
   }

   private boolean ableToAttack() {
      return this.mob.getTarget() != null && this.mob.getMainHandItem().has(Barched.DataComponents.KINETIC_WEAPON);
   }

   private int getKineticWeaponUseDuration() {
      int i = (Integer) Optional.ofNullable((KineticWeapon)this.mob.getMainHandItem().get(Barched.DataComponents.KINETIC_WEAPON)).map(KineticWeapon::computeDamageUseDuration).orElse(0);
      return reducedTickDelay(i);
   }

   public boolean canContinueToUse() {
      return this.state != null && !this.state.done && this.ableToAttack();
   }

   public void start() {
      super.start();
      this.mob.setAggressive(true);
      this.state = new SpearUseGoal.SpearUseState();
   }

   public void stop() {
      super.stop();
      this.mob.getNavigation().stop();
      this.mob.setAggressive(false);
      this.state = null;
      this.mob.stopUsingItem();
   }

   public void tick() {
      if (this.state != null) {
         LivingEntity livingEntity = this.mob.getTarget();
         double d = this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
         Entity entity = this.mob.getRootVehicle();
         float f = 1.0F;
         if (entity instanceof Mob) {
            Mob mob = (Mob)entity;
            f = ((MobBridge) mob).chargeSpeedModifier();
         }

         int i = this.mob.isPassenger() ? 2 : 0;
         this.mob.lookAt(livingEntity, 30.0F, 30.0F);
         this.mob.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
         if (this.state.notEngagedYet()) {
            if (d > (double)this.approachDistanceSq) {
               this.mob.getNavigation().moveTo((Entity)livingEntity, (double)f * this.speedModifierWhenRepositioning);
               return;
            }

            this.state.startEngagement(this.getKineticWeaponUseDuration());
            this.mob.startUsingItem(InteractionHand.MAIN_HAND);
         }

         double e;
         if (this.state.tickAndCheckEngagement()) {
            this.mob.stopUsingItem();
            e = Math.sqrt(d);
            this.state.awayPos = this.getPosAway0(this.mob, Math.max(0.0D, (double)(9 + i) - e), Math.max(1.0D, (double)(11 + i) - e), 7, livingEntity.position());
            this.state.fleeingTime = 1;
         }

         if (!this.state.tickAndCheckFleeing()) {
            if (this.state.awayPos != null) {
               this.mob.getNavigation().moveTo(this.state.awayPos.x, this.state.awayPos.y, this.state.awayPos.z, (double)f * this.speedModifierWhenRepositioning);
               if (this.mob.getNavigation().isDone()) {
                  if (this.state.fleeingTime > 0) {
                     this.state.done = true;
                     return;
                  }

                  this.state.awayPos = null;
               }
            } else {
               this.mob.getNavigation().moveTo((Entity)livingEntity, (double)f * this.speedModifierWhenCharging);
               if (d < (double)this.targetInRangeRadiusSq || this.mob.getNavigation().isDone()) {
                  e = Math.sqrt(d);
                  this.state.awayPos = this.getPosAway0(this.mob, (double)(6 + i) - e, (double)(7 + i) - e, 7, livingEntity.position());
               }
            }

         }
      }
   }

   public static class SpearUseState {
      private int engageTime = -1;
      int fleeingTime = -1;
      @Nullable
      Vec3 awayPos;
      boolean done = false;

      public boolean notEngagedYet() {
         return this.engageTime < 0;
      }

      public void startEngagement(int i) {
         this.engageTime = i;
      }

      public boolean tickAndCheckEngagement() {
         if (this.engageTime > 0) {
            --this.engageTime;
            if (this.engageTime == 0) {
               return true;
            }
         }

         return false;
      }

      public boolean tickAndCheckFleeing() {
         if (this.fleeingTime > 0) {
            ++this.fleeingTime;
            if ((double)this.fleeingTime > SpearUseGoal.MAX_FLEEING_TIME) {
               this.done = true;
               return true;
            }
         }

         return false;
      }
   }
}
