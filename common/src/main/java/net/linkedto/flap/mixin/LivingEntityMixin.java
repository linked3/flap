package net.linkedto.flap.mixin;

import net.linkedto.flap.FlapCompat;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    protected boolean canGlide() { return false; }

    @Inject(method = "travel", at = @At("HEAD"))
    private void flap$boostElytra(Vec3 movementInput, CallbackInfo ci) {
        if ((Object) this instanceof Player player && player.isFallFlying() && !player.isSpectator() && player.getFoodData().getFoodLevel() > 0 && player.zza > 0) {
            Vec3 velocity = player.getDeltaMovement();
            Vec3 look = player.getLookAngle();
            float upwardsAngleOfAttack = Mth.degreesDifferenceAbs(player.getXRot(), -90.0F);
            float speed = 0.0125F * (upwardsAngleOfAttack <= 15.0F ? 2.75F : 1.0F);

            player.setDeltaMovement(velocity.add(
                    look.x * speed + (look.x * 1.5D - velocity.x) * speed,
                    look.y * speed + (look.y * 1.5D - velocity.y) * speed,
                    look.z * speed + (look.z * 1.5D - velocity.z) * speed
            ));
        }
    }

    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true)
    private void flap$updateFallFlying(CallbackInfo ci) {
        if (!((Object) this instanceof Player player)) return;
        if (this.canGlide()) return;
        if (!FlapCompat.hasTrinketElytra(player)) return;
        if (player.level().isClientSide()) return;

        player.checkFallDistanceAccumulation();
        if (player.onGround() || player.isPassenger() || player.hasEffect(MobEffects.LEVITATION)) {
            player.stopFallFlying();
            ci.cancel();
            return;
        }

        if (!player.isFallFlying()) {
            player.startFallFlying();
        }

        ci.cancel();
    }
}
