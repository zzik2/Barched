package zzik2.barched.neoforge;

import cpw.mods.modlauncher.Environment;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import zzik2.barched.Barched;
import zzik2.barched.BarchedClient;

@Mod(Barched.MOD_ID)
public final class BarchedNeoForge {
    public BarchedNeoForge() {
        // Run our common setup.
        Barched.init();

        if (FMLEnvironment.dist.isClient()) {
            BarchedClient.init();
        }
    }
}
