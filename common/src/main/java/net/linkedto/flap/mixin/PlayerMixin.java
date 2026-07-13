package net.linkedto.flap.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void flap$elytraHungerDrain(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player.level().isClientSide()) return;
        if (!player.isFallFlying() || player.isCreative()) return;
        if (player.getFoodData().getFoodLevel() <= 0) return;
        player.getFoodData().addExhaustion(0.04f);
    }
}
