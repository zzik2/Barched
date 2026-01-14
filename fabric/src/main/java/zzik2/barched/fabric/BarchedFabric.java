package zzik2.barched.fabric;

import net.fabricmc.api.ModInitializer;
import zzik2.barched.Barched;

public final class BarchedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Barched.init();
    }
}
