package net.linkedto.flap.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void flap$skipVanillaElytraPhysics(Vec3 movementInput, CallbackInfo ci) {
        if ((Object) this instanceof Player player && player.isFallFlying()) {
            ci.cancel();
        }
    }
}
