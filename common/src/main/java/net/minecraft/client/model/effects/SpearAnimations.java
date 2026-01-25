package net.minecraft.client.model.effects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Ease;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.KineticWeapon;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import zzik2.barched.Barched;
import zzik2.barched.bridge.entity.LivingEntityBridge;
import zzik2.barched.mixin.accessor.LivingEntityAccessor;
import zzik2.barched.mixin.accessor.client.HumanoidModelAccessor;
import zzik2.zreflex.reflection.ZReflectionTool;

@Environment(EnvType.CLIENT)
public class SpearAnimations {
   static float progress(float f, float g, float h) {
      return Mth.clamp(Mth.inverseLerp(f, g, h), 0.0F, 1.0F);
   }

   public static <T extends LivingEntity> void thirdPersonHandUse(ModelPart modelPart, ModelPart modelPart2, boolean bl, ItemStack itemStack, T livingEntity) {
      float tick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      int i = bl ? 1 : -1;
      modelPart.yRot = -0.1F * (float)i + modelPart2.yRot;
      modelPart.xRot = -1.5707964F + modelPart2.xRot + 0.8F;
      if (livingEntity.isFallFlying() || ((LivingEntityAccessor) livingEntity).getSwimAmount() > 0.0F) {
         modelPart.xRot -= 0.9599311F;
      }

      modelPart.yRot = 0.017453292F * Math.clamp(57.295776F * modelPart.yRot, -60.0F, 60.0F);
      modelPart.xRot = 0.017453292F * Math.clamp(57.295776F * modelPart.xRot, -120.0F, 30.0F);
      if (!(((LivingEntityBridge) livingEntity).getTicksUsingItem(tick) <= 0.0F) && (!livingEntity.isUsingItem() || livingEntity.getUsedItemHand() == (bl ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND))) {
         KineticWeapon kineticWeapon = (KineticWeapon)itemStack.get(Barched.DataComponents.KINETIC_WEAPON);
         if (kineticWeapon != null) {
            SpearAnimations.UseParams useParams = SpearAnimations.UseParams.fromKineticWeapon(kineticWeapon, ((LivingEntityBridge) livingEntity).getTicksUsingItem(tick));
            modelPart.yRot += (float)(-i) * useParams.swayScaleFast() * 0.017453292F * useParams.swayIntensity() * 1.0F;
            modelPart.zRot += (float)(-i) * useParams.swayScaleSlow() * 0.017453292F * useParams.swayIntensity() * 0.5F;
            modelPart.xRot += 0.017453292F * (-40.0F * useParams.raiseProgressStart() + 30.0F * useParams.raiseProgressMiddle() + -20.0F * useParams.raiseProgressEnd() + 20.0F * useParams.lowerProgress() + 10.0F * useParams.raiseBackProgress() + 0.6F * useParams.swayScaleSlow() * useParams.swayIntensity());
         }
      }
   }

   public static <S extends LivingEntity> void thirdPersonUseItem(S livingEntity, PoseStack poseStack, float f, HumanoidArm humanoidArm, ItemStack itemStack) {
      KineticWeapon kineticWeapon = (KineticWeapon)itemStack.get(Barched.DataComponents.KINETIC_WEAPON);
      if (kineticWeapon != null && f != 0.0F) {
         float tick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
         float g = Ease.inQuad(progress(livingEntity.getAttackAnim(tick), 0.05F, 0.2F));
         float h = Ease.inOutExpo(progress(livingEntity.getAttackAnim(tick), 0.4F, 1.0F));
         SpearAnimations.UseParams useParams = SpearAnimations.UseParams.fromKineticWeapon(kineticWeapon, f);
         int i = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
         float j = 1.0F - Ease.outBack(1.0F - useParams.raiseProgress());
         float k = 0.125F;
         float l = hitFeedbackAmount(((LivingEntityBridge) livingEntity).getTicksSinceLastKineticHitFeedback(tick));
         poseStack.translate(0.0D, (double)(-l) * 0.4D, (double)(-kineticWeapon.forwardMovement() * (j - useParams.raiseBackProgress()) + l));
         poseStack.rotateAround(Axis.XN.rotationDegrees(70.0F * (useParams.raiseProgress() - useParams.raiseBackProgress()) - 40.0F * (g - h)), 0.0F, -0.03125F, 0.125F);
         poseStack.rotateAround(Axis.YP.rotationDegrees((float)(i * 90) * (useParams.raiseProgress() - useParams.swayProgress() + 3.0F * h + g)), 0.0F, 0.0F, 0.125F);
      }
   }

   public static <T extends LivingEntity> void thirdPersonAttackHand(HumanoidModel<T> humanoidModel, T livingEntity) {
      float tick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float f = livingEntity.getAttackAnim(tick);
      HumanoidArm humanoidArm = getAttackArm(livingEntity);
      ModelPart var10000 = humanoidModel.rightArm;
      var10000.yRot -= humanoidModel.body.yRot;
      var10000 = humanoidModel.leftArm;
      var10000.yRot -= humanoidModel.body.yRot;
      var10000 = humanoidModel.leftArm;
      var10000.xRot -= humanoidModel.body.yRot;
      float g = Ease.inOutSine(progress(f, 0.0F, 0.05F));
      float h = Ease.inQuad(progress(f, 0.05F, 0.2F));
      float i = Ease.inOutExpo(progress(f, 0.4F, 1.0F));
      var10000 = ((HumanoidModelAccessor) humanoidModel).getArm(humanoidArm);
      var10000.xRot += (90.0F * g - 120.0F * h + 30.0F * i) * 0.017453292F;
   }

   private static HumanoidArm getAttackArm(LivingEntity livingEntity) {
      HumanoidArm humanoidArm = livingEntity.getMainArm();
      return livingEntity.swingingArm == InteractionHand.MAIN_HAND ? humanoidArm : humanoidArm.getOpposite();
   }

   public static <S extends LivingEntity> void thirdPersonAttackItem(S livingEntity, PoseStack poseStack) {
      float tick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float h = livingEntity.getAttackAnim(tick);
      if (!(h <= 0.0F)) {
         KineticWeapon kineticWeapon = (KineticWeapon) ((LivingEntityBridge) livingEntity).barched$getMainHandItemStack().get(Barched.DataComponents.KINETIC_WEAPON);
         float f = kineticWeapon != null ? kineticWeapon.forwardMovement() : 0.0F;
         float g = 0.125F;
         float i = Ease.inQuad(progress(h, 0.05F, 0.2F));
         float j = Ease.inOutExpo(progress(h, 0.4F, 1.0F));
         poseStack.rotateAround(Axis.XN.rotationDegrees(70.0F * (i - j)), 0.0F, -0.125F, 0.125F);
         poseStack.translate(0.0F, f * (i - j), 0.0F);
      }
   }

   private static float hitFeedbackAmount(float f) {
      return 0.4F * (Ease.outQuart(progress(f, 1.0F, 3.0F)) - Ease.inOutSine(progress(f, 3.0F, 10.0F)));
   }

   public static void firstPersonUse(float f, PoseStack poseStack, float g, HumanoidArm humanoidArm, ItemStack itemStack) {
      KineticWeapon kineticWeapon = (KineticWeapon) itemStack.get(Barched.DataComponents.KINETIC_WEAPON);
      if (kineticWeapon != null) {
         SpearAnimations.UseParams useParams = SpearAnimations.UseParams.fromKineticWeapon(kineticWeapon, g);
         int i = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
         poseStack.translate((double)((float)i * (useParams.raiseProgress() * 0.15F + useParams.raiseProgressEnd() * -0.05F + useParams.swayProgress() * -0.1F + useParams.swayScaleSlow() * 0.005F)), (double)(useParams.raiseProgress() * -0.075F + useParams.raiseProgressMiddle() * 0.075F + useParams.swayScaleFast() * 0.01F), (double)useParams.raiseProgressStart() * 0.05D + (double)useParams.raiseProgressEnd() * -0.05D + (double)(useParams.swayScaleSlow() * 0.005F));
         poseStack.rotateAround(Axis.XP.rotationDegrees(-65.0F * Ease.inOutBack(useParams.raiseProgress()) - 35.0F * useParams.lowerProgress() + 100.0F * useParams.raiseBackProgress() + -0.5F * useParams.swayScaleFast()), 0.0F, 0.1F, 0.0F);
         poseStack.rotateAround(Axis.YN.rotationDegrees((float)i * (-90.0F * progress(useParams.raiseProgress(), 0.5F, 0.55F) + 90.0F * useParams.swayProgress() + 2.0F * useParams.swayScaleSlow())), (float)i * 0.15F, 0.0F, 0.0F);
         poseStack.translate(0.0F, -hitFeedbackAmount(f), 0.0F);
      }
   }

   public static void firstPersonAttack(float f, PoseStack poseStack, int i, HumanoidArm humanoidArm) {
      float g = Ease.inOutSine(progress(f, 0.0F, 0.05F));
      float h = Ease.outBack(progress(f, 0.05F, 0.2F));
      float j = Ease.inOutExpo(progress(f, 0.4F, 1.0F));
      poseStack.translate((float)i * 0.1F * (g - h), -0.075F * (g - j), 0.65F * (g - h));
      poseStack.mulPose(Axis.XP.rotationDegrees(-70.0F * (g - j)));
      poseStack.translate(0.0D, 0.0D, -0.25D * (double)(j - h));
   }

   @Environment(EnvType.CLIENT)
   static record UseParams(float raiseProgress, float raiseProgressStart, float raiseProgressMiddle, float raiseProgressEnd, float swayProgress, float lowerProgress, float raiseBackProgress, float swayIntensity, float swayScaleSlow, float swayScaleFast) {
      UseParams(float raiseProgress, float raiseProgressStart, float raiseProgressMiddle, float raiseProgressEnd, float swayProgress, float lowerProgress, float raiseBackProgress, float swayIntensity, float swayScaleSlow, float swayScaleFast) {
         this.raiseProgress = raiseProgress;
         this.raiseProgressStart = raiseProgressStart;
         this.raiseProgressMiddle = raiseProgressMiddle;
         this.raiseProgressEnd = raiseProgressEnd;
         this.swayProgress = swayProgress;
         this.lowerProgress = lowerProgress;
         this.raiseBackProgress = raiseBackProgress;
         this.swayIntensity = swayIntensity;
         this.swayScaleSlow = swayScaleSlow;
         this.swayScaleFast = swayScaleFast;
      }

      public static SpearAnimations.UseParams fromKineticWeapon(KineticWeapon kineticWeapon, float f) {
         int i = kineticWeapon.delayTicks();
         int j = (Integer)kineticWeapon.dismountConditions().map(KineticWeapon.Condition::maxDurationTicks).orElse(0) + i;
         int k = j - 20;
         int l = (Integer)kineticWeapon.knockbackConditions().map(KineticWeapon.Condition::maxDurationTicks).orElse(0) + i;
         int m = l - 40;
         int n = (Integer)kineticWeapon.damageConditions().map(KineticWeapon.Condition::maxDurationTicks).orElse(0) + i;
         float g = SpearAnimations.progress(f, 0.0F, (float)i);
         float h = SpearAnimations.progress(g, 0.0F, 0.5F);
         float o = SpearAnimations.progress(g, 0.5F, 0.8F);
         float p = SpearAnimations.progress(g, 0.8F, 1.0F);
         float q = SpearAnimations.progress(f, (float)k, (float)m);
         float r = Ease.outCubic(Ease.inOutElastic(SpearAnimations.progress(f - 20.0F, (float)m, (float)l)));
         float s = SpearAnimations.progress(f, (float)(n - 5), (float)n);
         float t = 2.0F * Ease.outCirc(q) - 2.0F * Ease.inCirc(s);
         float u = (float) ZReflectionTool.invokeStaticMethod(Mth.class, "sin", (double)(f * 19.0F * 0.017453292F)) * t;
         float v = (float) ZReflectionTool.invokeStaticMethod(Mth.class, "sin", (double)(f * 30.0F * 0.017453292F)) * t;
         return new SpearAnimations.UseParams(g, h, o, p, q, r, s, t, u, v);
      }

      public float raiseProgress() {
         return this.raiseProgress;
      }

      public float raiseProgressStart() {
         return this.raiseProgressStart;
      }

      public float raiseProgressMiddle() {
         return this.raiseProgressMiddle;
      }

      public float raiseProgressEnd() {
         return this.raiseProgressEnd;
      }

      public float swayProgress() {
         return this.swayProgress;
      }

      public float lowerProgress() {
         return this.lowerProgress;
      }

      public float raiseBackProgress() {
         return this.raiseBackProgress;
      }

      public float swayIntensity() {
         return this.swayIntensity;
      }

      public float swayScaleSlow() {
         return this.swayScaleSlow;
      }

      public float swayScaleFast() {
         return this.swayScaleFast;
      }
   }
}
