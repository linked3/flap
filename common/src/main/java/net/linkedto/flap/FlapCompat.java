package net.linkedto.flap;

import net.minecraft.world.entity.player.Player;
import java.util.function.Predicate;

public final class FlapCompat {
    // static hook for platform-specific trinket elytra checks
    private static Predicate<Player> trinketElytraCheck = p -> false;

    public static boolean hasTrinketElytra(Player player) {
        return trinketElytraCheck.test(player);
    }

    public static void setTrinketElytraCheck(Predicate<Player> check) {
        trinketElytraCheck = check;
    }
}
