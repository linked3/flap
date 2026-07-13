package net.linkedto.flap.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow
    private Entity entity;

    @Shadow
    private float yRot;

    @Unique
    private float flap$prevCameraYaw = Float.NaN;

    @Unique
    private float flap$roll;

    @ModifyArg(method = "setRotation", at = @At(value = "INVOKE", target = "Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;", remap = false), index = 2)
    private float flap$addElytraRoll(float roll) {
        if (entity instanceof LocalPlayer player && player.isFallFlying()) {
            if (Float.isNaN(flap$prevCameraYaw)) {
                flap$prevCameraYaw = yRot;
                return roll;
            }

            float yawDelta = Mth.wrapDegrees(yRot - flap$prevCameraYaw);
            flap$prevCameraYaw = yRot;

            float targetRoll = Mth.clamp(yawDelta * 4.0F, -25F, 25F) * Mth.DEG_TO_RAD;
            flap$roll += (targetRoll - flap$roll) * 0.3F;

            return roll + flap$roll;
        }

        flap$prevCameraYaw = Float.NaN;
        flap$roll *= 0.85F;
        return roll;
    }
}
