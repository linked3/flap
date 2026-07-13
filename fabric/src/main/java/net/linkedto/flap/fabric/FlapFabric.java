package net.linkedto.flap.fabric;

import net.fabricmc.api.ModInitializer;
import net.linkedto.flap.Flap;

public final class FlapFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Flap.init();
    }
}
