package zzik2.barched;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Barched.MOD_ID)
public class BarchedConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int camelHuskSpawnChance = 10;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int zombifiedPiglinSpearSpawnChance = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int piglinSpearSpawnChance = 10;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int zombieOverrideSpearSpawnChance = 0;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int zombieHorseSpawnWeight = 1;

    public float getCamelHuskSpawnChanceAsFloat() {
        return camelHuskSpawnChance / 100.0F;
    }

    public float getZombifiedPiglinSpearSpawnChanceAsFloat() {
        return zombifiedPiglinSpearSpawnChance / 100.0F;
    }

    public float getPiglinSpearSpawnChanceAsFloat() {
        return piglinSpearSpawnChance / 100.0F;
    }

    public float getZombieOverrideSpearSpawnChanceAsFloat() {
        return zombieOverrideSpearSpawnChance / 100.0F;
    }

    public int getZombieHorseSpawnWeight() {
        return zombieHorseSpawnWeight;
    }
}
