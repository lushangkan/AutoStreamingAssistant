package cn.cutemc.autostreamingassistant.network.packets;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import cn.cutemc.autostreamingassistant.ClientStatus;
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

public class ClientRequestStatusHandler implements ClientPlayNetworking.PlayChannelHandler {

    public ClientRequestStatusHandler() {
        ClientPlayNetworking.registerGlobalReceiver(ModPacketID.REQUEST_STATUS, this);
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        String jsonStr = buf.readString();
        Gson gson = new Gson();
        RequestStatusMessage requestStatusMessage = gson.fromJson(jsonStr, RequestStatusMessage.class);

        ClientStatusMessage clientStatusMessage = new ClientStatusMessage();
        clientStatusMessage.setStatus(AutoStreamingAssistant.CAMERA.cameraPlayerUUID == null ? ClientStatus.READY : ClientStatus.BOUND);
        clientStatusMessage.setVersion(AutoStreamingAssistant.VERSION);
        String resultJson = gson.toJson(clientStatusMessage);

        responseSender.sendPacket(ModPacketID.CLIENT_STATUS, PacketByteBufs.empty().writeString(resultJson));
    }

    @Getter
    @Setter
    static class RequestStatusMessage {
        private String version;
    }

    @Getter
    @Setter
    static class ClientStatusMessage {
        private ClientStatus status;
        private String version;
    }
}
