package zzik2.barched;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = Barched.MOD_ID)
public class BarchedConfig implements ConfigData {

    private static final int MAX_NATURAL_SPAWN_WEIGHT = 1000;
    private static final List<String> DEFAULT_ZOMBIE_HORSE_SPAWN_BIOMES = List.of(
            "minecraft:plains",
            "minecraft:sunflower_plains",
            "minecraft:snowy_plains",
            "minecraft:savanna",
            "minecraft:savanna_plateau",
            "minecraft:windswept_savanna"
    );
    private static final List<String> DEFAULT_PARCHED_SPAWN_BIOMES = List.of(
            "minecraft:desert"
    );

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int camelHuskSpawnChance = 10;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int zombifiedPiglinSpearSpawnChance = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int piglinOverrideSpearSpawnChance = 0;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int zombieOverrideSpearSpawnChance = 0;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = MAX_NATURAL_SPAWN_WEIGHT)
    public int zombieHorseSpawnWeight = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public List<String> zombieHorseSpawnBiomes = new ArrayList<>(DEFAULT_ZOMBIE_HORSE_SPAWN_BIOMES);

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = MAX_NATURAL_SPAWN_WEIGHT)
    public int parchedSpawnWeight = 50;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public List<String> parchedSpawnBiomes = new ArrayList<>(DEFAULT_PARCHED_SPAWN_BIOMES);

    @Override
    public void validatePostLoad() {
        zombieHorseSpawnWeight = clampSpawnWeight(zombieHorseSpawnWeight);
        parchedSpawnWeight = clampSpawnWeight(parchedSpawnWeight);
        zombieHorseSpawnBiomes = mutableOrDefault(zombieHorseSpawnBiomes, DEFAULT_ZOMBIE_HORSE_SPAWN_BIOMES);
        parchedSpawnBiomes = mutableOrDefault(parchedSpawnBiomes, DEFAULT_PARCHED_SPAWN_BIOMES);
    }

    public float getCamelHuskSpawnChanceAsFloat() {
        return camelHuskSpawnChance / 100.0F;
    }

    public float getZombifiedPiglinSpearSpawnChanceAsFloat() {
        return zombifiedPiglinSpearSpawnChance / 100.0F;
    }

    public float getPiglinOverrideSpearSpawnChanceAsFloat() {
        return piglinOverrideSpearSpawnChance / 100.0F;
    }

    public float getZombieOverrideSpearSpawnChanceAsFloat() {
        return zombieOverrideSpearSpawnChance / 100.0F;
    }

    private static int clampSpawnWeight(int weight) {
        return Math.max(0, Math.min(MAX_NATURAL_SPAWN_WEIGHT, weight));
    }

    private static List<String> mutableOrDefault(List<String> configured, List<String> defaults) {
        return configured == null ? new ArrayList<>(defaults) : new ArrayList<>(configured);
    }
}
