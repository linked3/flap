package net.linkedto.flap.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void flap$onTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!player.isFallFlying() || player.isCreative() || player.isSpectator()) return;

        Vec3 velocity = player.getDeltaMovement();

        if (player.getFoodData().getFoodLevel() > 0 && player.zza > 0) {
            Vec3 look = player.getLookAngle();
            float upwardsAngleOfAttack = Mth.degreesDifferenceAbs(player.getXRot(), -90.0F);
            float speed = 0.0125F * (upwardsAngleOfAttack <= 15.0F ? 2.75F : 1.0F);

            player.setDeltaMovement(velocity.add(
                    look.x * speed + (look.x * 1.5D - velocity.x) * speed,
                    look.y * speed + (look.y * 1.5D - velocity.y) * speed,
                    look.z * speed + (look.z * 1.5D - velocity.z) * speed
            ));

            if (!player.level().isClientSide()) {
                player.getFoodData().addExhaustion(0.04f);
            }
        }

        player.move(MoverType.SELF, player.getDeltaMovement());
    }
}
