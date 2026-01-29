package net.minecraft.util;

public class Ease {

   public static float inBack(float f) {
      float g = 1.70158F;
      float h = 2.70158F;
      return Mth.square(f) * (2.70158F * f - 1.70158F);
   }

   public static float inBounce(float f) {
      return 1.0F - outBounce(1.0F - f);
   }

   public static float inCubic(float f) {
      return cube(f);
   }

   public static float inElastic(float f) {
      if (f == 0.0F) {
         return 0.0F;
      } else if (f == 1.0F) {
         return 1.0F;
      } else {
         float g = 2.0943952F;
         return (float)(-Math.pow(2.0D, 10.0D * (double)f - 10.0D) * Math.sin(((double)f * 10.0D - 10.75D) * 2.094395160675049D));
      }
   }

   public static float inExpo(float f) {
      return f == 0.0F ? 0.0F : (float)Math.pow(2.0D, 10.0D * (double)f - 10.0D);
   }

   public static float inQuart(float f) {
      return Mth.square(Mth.square(f));
   }

   public static float inQuint(float f) {
      return Mth.square(Mth.square(f)) * f;
   }

   public static float inSine(float f) {
      float r = Mth.cos(f * 1.5707964F);
      return 1.0F - r;
   }

   public static float inOutBounce(float f) {
      return f < 0.5F ? (1.0F - outBounce(1.0F - 2.0F * f)) / 2.0F : (1.0F + outBounce(2.0F * f - 1.0F)) / 2.0F;
   }

   public static float inOutCirc(float f) {
      return f < 0.5F ? (float)((1.0D - Math.sqrt(1.0D - Math.pow(2.0D * (double)f, 2.0D))) / 2.0D) : (float)((Math.sqrt(1.0D - Math.pow(-2.0D * (double)f + 2.0D, 2.0D)) + 1.0D) / 2.0D);
   }

   public static float inOutCubic(float f) {
      return f < 0.5F ? 4.0F * cube(f) : (float)(1.0D - Math.pow(-2.0D * (double)f + 2.0D, 3.0D) / 2.0D);
   }

   public static float inOutQuad(float f) {
      return f < 0.5F ? 2.0F * Mth.square(f) : (float)(1.0D - Math.pow(-2.0D * (double)f + 2.0D, 2.0D) / 2.0D);
   }

   public static float inOutQuart(float f) {
      return f < 0.5F ? 8.0F * Mth.square(Mth.square(f)) : (float)(1.0D - Math.pow(-2.0D * (double)f + 2.0D, 4.0D) / 2.0D);
   }

   public static float inOutQuint(float f) {
      return (double)f < 0.5D ? 16.0F * f * f * f * f * f : (float)(1.0D - Math.pow(-2.0D * (double)f + 2.0D, 5.0D) / 2.0D);
   }

   public static float outBounce(float f) {
      float g = 7.5625F;
      float h = 2.75F;
      if (f < 0.36363637F) {
         return 7.5625F * Mth.square(f);
      } else if (f < 0.72727275F) {
         return 7.5625F * Mth.square(f - 0.54545456F) + 0.75F;
      } else {
         return (double)f < 0.9090909090909091D ? 7.5625F * Mth.square(f - 0.8181818F) + 0.9375F : 7.5625F * Mth.square(f - 0.95454544F) + 0.984375F;
      }
   }

   public static float outElastic(float f) {
      float g = 2.0943952F;
      if (f == 0.0F) {
         return 0.0F;
      } else {
         return f == 1.0F ? 1.0F : (float)(Math.pow(2.0D, -10.0D * (double)f) * Math.sin(((double)f * 10.0D - 0.75D) * 2.094395160675049D) + 1.0D);
      }
   }

   public static float outExpo(float f) {
      return f == 1.0F ? 1.0F : 1.0F - (float)Math.pow(2.0D, -10.0D * (double)f);
   }

   public static float outQuad(float f) {
      return 1.0F - Mth.square(1.0F - f);
   }

   public static float outQuint(float f) {
      return 1.0F - (float)Math.pow(1.0D - (double)f, 5.0D);
   }

   public static float outSine(float f) {
      float r = Mth.sin(f * 1.5707964F);
      return r;
   }

   public static float inOutSine(float f) {
      float r = Mth.cos(3.1415927F * f);
      return -(r - 1.0F) / 2.0F;
   }

   public static float outBack(float f) {
      float g = 1.70158F;
      float h = 2.70158F;
      return 1.0F + 2.70158F * cube(f - 1.0F) + 1.70158F * Mth.square(f - 1.0F);
   }

   public static float outQuart(float f) {
      return 1.0F - Mth.square(Mth.square(1.0F - f));
   }

   public static float outCubic(float f) {
      return 1.0F - cube(1.0F - f);
   }

   public static float inOutExpo(float f) {
      if (f < 0.5F) {
         return f == 0.0F ? 0.0F : (float)(Math.pow(2.0D, 20.0D * (double)f - 10.0D) / 2.0D);
      } else {
         return f == 1.0F ? 1.0F : (float)((2.0D - Math.pow(2.0D, -20.0D * (double)f + 10.0D)) / 2.0D);
      }
   }

   public static float inQuad(float f) {
      return f * f;
   }

   public static float outCirc(float f) {
      return (float)Math.sqrt((double)(1.0F - Mth.square(f - 1.0F)));
   }

   public static float inOutElastic(float f) {
      float g = 1.3962635F;
      if (f == 0.0F) {
         return 0.0F;
      } else if (f == 1.0F) {
         return 1.0F;
      } else {
         double d = Math.sin((20.0D * (double)f - 11.125D) * 1.3962634801864624D);
         return f < 0.5F ? (float)(-(Math.pow(2.0D, 20.0D * (double)f - 10.0D) * d) / 2.0D) : (float)(Math.pow(2.0D, -20.0D * (double)f + 10.0D) * d / 2.0D + 1.0D);
      }
   }

   public static float inCirc(float f) {
      return (float)(-Math.sqrt((double)(1.0F - f * f))) + 1.0F;
   }

   public static float inOutBack(float f) {
      float g = 1.70158F;
      float h = 2.5949094F;
      if (f < 0.5F) {
         return 4.0F * f * f * (7.189819F * f - 2.5949094F) / 2.0F;
      } else {
         float i = 2.0F * f - 2.0F;
         return (i * i * (3.5949094F * i + 2.5949094F) + 2.0F) / 2.0F;
      }
   }

   // Mth.class
   public static float cube(float f) {
      return f * f * f;
   }
}