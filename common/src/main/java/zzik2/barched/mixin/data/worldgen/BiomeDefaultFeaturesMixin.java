package zzik2.barched.mixin.data.worldgen;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;

@Mixin(BiomeDefaultFeatures.class)
public class BiomeDefaultFeaturesMixin {

    @Inject(method = "desertSpawns", at = @At("TAIL"))
    private static void barched$desertSpawns(MobSpawnSettings.Builder builder, CallbackInfo ci) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Barched.EntityType.PARCHED, 50, 4, 4));
    }

    @Inject(method = "monsters", at = @At("TAIL"))
    private static void barched$monsters(MobSpawnSettings.Builder builder, int i, int j, int k, boolean bl, CallbackInfo ci) {
        int zombieHorseWeight = Barched.getConfig().getZombieHorseSpawnWeight();
        if (zombieHorseWeight > 0) {
            builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_HORSE, zombieHorseWeight, 1, 1));
        }
    }
}
