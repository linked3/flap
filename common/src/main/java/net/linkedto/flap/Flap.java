package net.linkedto.flap;

import net.linkedto.flap.network.FlapNetwork;
import net.minecraft.resources.Identifier;

public final class Flap {
    // mod entrypoint — id helper and network init
    public static final String MOD_ID = "flap";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        FlapNetwork.init();
    }
}
