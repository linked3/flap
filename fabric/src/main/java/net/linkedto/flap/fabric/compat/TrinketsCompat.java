package net.linkedto.flap.fabric.compat;

import eu.pb4.trinkets.api.TrinketsApi;
import net.linkedto.flap.FlapCompat;
import net.minecraft.world.item.Items;

public final class TrinketsCompat {
    // checks cape slot for elytra via Trinkets-Updated API
    public static void init() {
        FlapCompat.setTrinketElytraCheck(player -> {
            var attachment = TrinketsApi.getAttachment(player);
            if (attachment == null) return false;
            return attachment.isEquipped(Items.ELYTRA, true);
        });
    }
}
