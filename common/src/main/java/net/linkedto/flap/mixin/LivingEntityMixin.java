package net.linkedto.flap.mixin;

import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class LivingEntityMixin {
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void flap$customFlight(Vec3 motion, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!player.isFallFlying() || player.isCreative()) return;
        if (player.level().isClientSide()) return;
        if (player.getFoodData().getFoodLevel() <= 0) return;

        Vec3 look = player.getLookAngle();
        player.setDeltaMovement(player.getDeltaMovement().add(look.scale(0.05)));
        player.move(MoverType.SELF, player.getDeltaMovement());
        ci.cancel();
    }
}
