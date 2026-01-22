package zzik2.barched;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Barched.MOD_ID)
public class BarchedConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int camelHuskSpawnChance = 10;

    public float getCamelHuskSpawnChanceAsFloat() {
        return camelHuskSpawnChance / 100.0F;
    }
}
