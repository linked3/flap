package net.linkedto.flap.mixin;

import net.linkedto.flap.network.FlapNetwork;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.architectury.networking.NetworkManager;

@Mixin(Player.class)
public class PlayerMixin {
    // sends c2s boost packet from client tick when looking up + pressing w
    @Inject(method = "tick", at = @At("TAIL"))
    private void flap$onTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!player.level().isClientSide()) return;
        if (!player.isFallFlying() || player.isSpectator()) return;
        if (player.getFoodData().getFoodLevel() <= 0) return;
        if (player.zza <= 0) return;
        if (player.getXRot() >= 0) return;

        NetworkManager.sendToServer(FlapNetwork.ApplyBoostPayload.INSTANCE);
    }
}
