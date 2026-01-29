package net.minecraft.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import zzik2.barched.Barched;

import java.util.Optional;

public class SpearMobsTrigger extends SimpleCriterionTrigger<SpearMobsTrigger.TriggerInstance> {
   public Codec<TriggerInstance> codec() {
      return SpearMobsTrigger.TriggerInstance.CODEC;
   }

   public void trigger(ServerPlayer serverPlayer, int i) {
      this.trigger(serverPlayer, (triggerInstance) -> {
         return triggerInstance.matches(i);
      });
   }

   public static record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Integer> count) implements SimpleCriterionTrigger.SimpleInstance {
      public static final Codec<SpearMobsTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> {
         return instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SpearMobsTrigger.TriggerInstance::player), ExtraCodecs.POSITIVE_INT.optionalFieldOf("count").forGetter(SpearMobsTrigger.TriggerInstance::count)).apply(instance, SpearMobsTrigger.TriggerInstance::new);
      });

      public TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Integer> count) {
         this.player = player;
         this.count = count;
      }

      public static Criterion<TriggerInstance> spearMobs(int i) {
         return Barched.CriteriaTriggers.SPEAR_MOBS_TRIGGER.createCriterion(new SpearMobsTrigger.TriggerInstance(Optional.empty(), Optional.of(i)));
      }

      public boolean matches(int i) {
         return this.count.isEmpty() || i >= (Integer)this.count.get();
      }

      public Optional<ContextAwarePredicate> player() {
         return this.player;
      }

      public Optional<Integer> count() {
         return this.count;
      }
   }
}
