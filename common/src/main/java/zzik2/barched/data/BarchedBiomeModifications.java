package zzik2.barched.data;

import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import zzik2.barched.Barched;

import java.util.Set;

public final class BarchedBiomeModifications {

    private static final Set<ResourceLocation> ZOMBIE_HORSE_BIOMES = Set.of(
            Biomes.PLAINS.location(),
            Biomes.SUNFLOWER_PLAINS.location(),
            Biomes.SNOWY_PLAINS.location(),
            Biomes.SAVANNA.location(),
            Biomes.SAVANNA_PLATEAU.location(),
            Biomes.WINDSWEPT_SAVANNA.location()
    );

    private BarchedBiomeModifications() {}

    public static void register() {
        BiomeModifications.addProperties(
                context -> context.getKey().map(ZOMBIE_HORSE_BIOMES::contains).orElse(false),
                (context, properties) -> properties.getSpawnProperties().addSpawn(
                        MobCategory.MONSTER,
                        new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_HORSE, 5, 1, 1)
                )
        );
        BiomeModifications.addProperties(
                context -> context.getKey().map(Biomes.DESERT.location()::equals).orElse(false),
                (context, properties) -> properties.getSpawnProperties().addSpawn(
                        MobCategory.MONSTER,
                        new MobSpawnSettings.SpawnerData(Barched.EntityType.PARCHED, 50, 4, 4)
                )
        );
    }
}
