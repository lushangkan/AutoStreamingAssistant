package cn.cutemc.autostreamingassistant.network.packets;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import cn.cutemc.autostreamingassistant.network.ModPacketID;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class ClientRequestBindStatusHandle implements ClientPlayNetworking.PlayChannelHandler {

    public ClientRequestBindStatusHandle() {
        ClientPlayNetworking.registerGlobalReceiver(ModPacketID.REQUEST_BIND_STATUS, this);
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BindStatusMessage bindStatusMessage = new BindStatusMessage();
        bindStatusMessage.setPlayerUuid(AutoStreamingAssistant.CAMERA.cameraPlayerUUID);

        responseSender.sendPacket(ModPacketID.BIND_STATUS, PacketByteBufs.empty().writeString(new Gson().toJson(bindStatusMessage)));
    }

    @Getter
    @Setter
    static class BindStatusMessage {
        private UUID playerUuid;
    }
}
