package net.linkedto.flap.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.linkedto.flap.Flap;

public final class FlapFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Flap.init();

        if (FabricLoader.getInstance().isModLoaded("trinkets_updated")) {
            net.linkedto.flap.fabric.compat.TrinketsCompat.init();
        }
    }
}
