package net.linkedto.flap.network;

import net.linkedto.flap.Flap;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class FlapNetwork {
    public record ApplyBoostPayload() implements CustomPacketPayload {
        public static final ApplyBoostPayload INSTANCE = new ApplyBoostPayload();
        public static final CustomPacketPayload.Type<ApplyBoostPayload> TYPE = new CustomPacketPayload.Type<>(Flap.id("apply_boost"));
        public static final StreamCodec<FriendlyByteBuf, ApplyBoostPayload> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public ApplyBoostPayload decode(FriendlyByteBuf buf) {
                return INSTANCE;
            }

            @Override
            public void encode(FriendlyByteBuf buf, ApplyBoostPayload payload) {
            }
        };

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void init() {
        NetworkManager.registerC2S(ApplyBoostPayload.TYPE, ApplyBoostPayload.STREAM_CODEC, (payload, context) -> {
            context.queue(() -> {
                Player player = context.getPlayer();
                if (player instanceof ServerPlayer sp && sp.isFallFlying() && !sp.isCreative() && !sp.isSpectator() && sp.getFoodData().getFoodLevel() > 0) {
                    sp.getFoodData().addExhaustion(0.04F);
                }
            });
        });
    }
}
