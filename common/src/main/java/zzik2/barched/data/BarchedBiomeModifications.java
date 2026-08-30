package zzik2.barched.data;

import com.mojang.logging.LogUtils;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.slf4j.Logger;
import zzik2.barched.Barched;
import zzik2.barched.BarchedConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class BarchedBiomeModifications {

    private static final Logger LOGGER = LogUtils.getLogger();

    private BarchedBiomeModifications() {}

    public static void register() {
        BarchedConfig config = Barched.getConfig();
        registerNaturalSpawn(
                "zombie horse",
                config.zombieHorseSpawnWeight,
                config.zombieHorseSpawnBiomes,
                () -> EntityType.ZOMBIE_HORSE,
                1,
                1
        );
        registerNaturalSpawn(
                "parched",
                config.parchedSpawnWeight,
                config.parchedSpawnBiomes,
                () -> Barched.EntityType.PARCHED,
                4,
                4
        );
    }

    private static void registerNaturalSpawn(String name, int weight, List<String> configuredBiomes, Supplier<EntityType<?>> entityType, int minGroupSize, int maxGroupSize) {
        if (weight <= 0) {
            return;
        }

        BiomeSelection selection = parseBiomeSelection(name, configuredBiomes);
        if (selection.isEmpty()) {
            LOGGER.warn("Natural spawning for {} is disabled because no valid biome selectors are configured", name);
            return;
        }

        BiomeModifications.addProperties(
                selection::matches,
                (context, properties) -> properties.getSpawnProperties().addSpawn(
                        MobCategory.MONSTER,
                        new MobSpawnSettings.SpawnerData(entityType.get(), weight, minGroupSize, maxGroupSize)
                )
        );
    }

    private static BiomeSelection parseBiomeSelection(String name, List<String> configuredBiomes) {
        Set<ResourceLocation> biomeIds = new HashSet<>();
        Set<TagKey<Biome>> biomeTags = new HashSet<>();

        if (configuredBiomes == null) {
            return new BiomeSelection(Set.of(), Set.of());
        }

        for (String configuredBiome : configuredBiomes) {
            if (configuredBiome == null || configuredBiome.isBlank()) {
                continue;
            }

            String selector = configuredBiome.trim();
            boolean isTag = selector.startsWith("#");
            String locationString = isTag ? selector.substring(1) : selector;
            ResourceLocation location = locationString.isBlank() ? null : ResourceLocation.tryParse(locationString);
            if (location == null) {
                LOGGER.warn("Ignoring invalid {} biome selector: {}", name, configuredBiome);
            } else if (isTag) {
                biomeTags.add(TagKey.create(Registries.BIOME, location));
            } else {
                biomeIds.add(location);
            }
        }

        return new BiomeSelection(Set.copyOf(biomeIds), Set.copyOf(biomeTags));
    }

    private record BiomeSelection(Set<ResourceLocation> biomeIds, Set<TagKey<Biome>> biomeTags) {

        private boolean matches(BiomeModifications.BiomeContext context) {
            return context.getKey().map(biomeIds::contains).orElse(false) || biomeTags.stream().anyMatch(context::hasTag);
        }

        private boolean isEmpty() {
            return biomeIds.isEmpty() && biomeTags.isEmpty();
        }
    }
}
