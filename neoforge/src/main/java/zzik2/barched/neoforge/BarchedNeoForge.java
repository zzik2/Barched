package zzik2.barched.neoforge;

import net.neoforged.fml.common.Mod;
import zzik2.barched.Barched;

@Mod(Barched.MOD_ID)
public final class BarchedNeoForge {
    public BarchedNeoForge() {
        // Run our common setup.
        Barched.init();
    }
}
