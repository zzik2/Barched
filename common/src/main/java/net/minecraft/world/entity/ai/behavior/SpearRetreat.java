package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.MobBridge;
import zzik2.barched.bridge.entity.ai.LandRandomPosBridge;

import java.util.Map;

public class SpearRetreat extends Behavior<PathfinderMob> implements LandRandomPosBridge {

   public static final int MIN_COOLDOWN_DISTANCE = 9;
   public static final int MAX_COOLDOWN_DISTANCE = 11;
   public static final int MAX_FLEEING_TIME = 100;
   double speedModifierWhenRepositioning;

   public SpearRetreat(double d) {
      super(Map.of(Barched.MemoryModuleType.SPEAR_STATUS, MemoryStatus.VALUE_PRESENT), 100);
      this.speedModifierWhenRepositioning = d;
   }

   @Nullable
   private LivingEntity getTarget(PathfinderMob pathfinderMob) {
      return (LivingEntity)pathfinderMob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
   }

   private boolean ableToAttack(PathfinderMob pathfinderMob) {
      return this.getTarget(pathfinderMob) != null && pathfinderMob.getMainHandItem().has(Barched.DataComponents.KINETIC_WEAPON);
   }

   protected boolean checkExtraStartConditions(ServerLevel serverLevel, PathfinderMob pathfinderMob) {
      if (this.ableToAttack(pathfinderMob) && !pathfinderMob.isUsingItem()) {
         if (pathfinderMob.getBrain().getMemory(Barched.MemoryModuleType.SPEAR_STATUS).orElse(SpearAttack.SpearStatus.APPROACH) != SpearAttack.SpearStatus.RETREAT) {
            return false;
         } else {
            LivingEntity livingEntity = this.getTarget(pathfinderMob);
            double d = pathfinderMob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            int i = pathfinderMob.isPassenger() ? 2 : 0;
            double e = Math.sqrt(d);
            Vec3 vec3 = this.getPosAway0(pathfinderMob, Math.max(0.0D, (double)(9 + i) - e), Math.max(1.0D, (double)(11 + i) - e), 7, livingEntity.position());
            if (vec3 == null) {
               return false;
            } else {
               pathfinderMob.getBrain().setMemory(Barched.MemoryModuleType.SPEAR_FLEEING_POSITION, vec3);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   protected void start(ServerLevel serverLevel, PathfinderMob pathfinderMob, long l) {
      pathfinderMob.setAggressive(true);
      pathfinderMob.getBrain().setMemory(Barched.MemoryModuleType.SPEAR_FLEEING_TIME, (int)0);
      super.start(serverLevel, pathfinderMob, l);
   }

   protected boolean canStillUse(ServerLevel serverLevel, PathfinderMob pathfinderMob, long l) {
      return (Integer)pathfinderMob.getBrain().getMemory(Barched.MemoryModuleType.SPEAR_FLEEING_TIME).orElse(100) < 100 && pathfinderMob.getBrain().getMemory(Barched.MemoryModuleType.SPEAR_FLEEING_POSITION).isPresent() && !pathfinderMob.getNavigation().isDone() && this.ableToAttack(pathfinderMob);
   }

   protected void tick(ServerLevel serverLevel, PathfinderMob pathfinderMob, long l) {
      LivingEntity livingEntity = this.getTarget(pathfinderMob);
      Entity entity = pathfinderMob.getRootVehicle();
      float var10000;
      if (entity instanceof Mob) {
         Mob mob = (Mob)entity;
         var10000 = ((MobBridge) mob).chargeSpeedModifier();
      } else {
         var10000 = 1.0F;
      }

      float f = var10000;
      pathfinderMob.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, (new EntityTracker(livingEntity, true)));
      pathfinderMob.getBrain().setMemory(Barched.MemoryModuleType.SPEAR_FLEEING_TIME, (pathfinderMob.getBrain().getMemory(Barched.MemoryModuleType.SPEAR_FLEEING_TIME).orElse(0) + 1));
      pathfinderMob.getBrain().getMemory(Barched.MemoryModuleType.SPEAR_FLEEING_POSITION).ifPresent((vec3) -> {
         pathfinderMob.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, (double)f * this.speedModifierWhenRepositioning);
      });
   }

   protected void stop(ServerLevel serverLevel, PathfinderMob pathfinderMob, long l) {
      pathfinderMob.getNavigation().stop();
      pathfinderMob.setAggressive(false);
      pathfinderMob.stopUsingItem();
      pathfinderMob.getBrain().eraseMemory(Barched.MemoryModuleType.SPEAR_FLEEING_TIME);
      pathfinderMob.getBrain().eraseMemory(Barched.MemoryModuleType.SPEAR_FLEEING_POSITION);
      pathfinderMob.getBrain().eraseMemory(Barched.MemoryModuleType.SPEAR_STATUS);
   }
}