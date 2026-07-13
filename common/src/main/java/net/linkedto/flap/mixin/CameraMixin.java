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

    @Unique
    private float flap$roll;

    @ModifyArg(method = "setRotation", at = @At(value = "INVOKE", target = "Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;", remap = false), index = 2)
    private float flap$addElytraRoll(float roll) {
        if (entity instanceof LocalPlayer player && player.isFallFlying()) {
            float yawPrev = player.yRotO;
            float yawCurr = player.getYRot();
            float yawDelta = Mth.wrapDegrees(yawCurr - yawPrev);

            float targetRoll = Mth.clamp(yawDelta * -0.3F, -20F, 20F) * Mth.DEG_TO_RAD;
            flap$roll += (targetRoll - flap$roll) * 0.15F;

            return roll + flap$roll;
        }

        flap$roll *= 0.85F;
        return roll;
    }
}
