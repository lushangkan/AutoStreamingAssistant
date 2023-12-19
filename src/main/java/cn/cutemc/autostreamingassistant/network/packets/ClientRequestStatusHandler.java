package cn.cutemc.autostreamingassistant.network.packets;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import cn.cutemc.autostreamingassistant.ClientStatus;
import cn.cutemc.autostreamingassistant.network.PacketID;
import cn.cutemc.autostreamingassistant.utils.BufferUtils;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.nio.charset.StandardCharsets;


@Log4j2
public class ClientRequestStatusHandler implements ClientPlayNetworking.PlayChannelHandler {

    public ClientRequestStatusHandler() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID.REQUEST_STATUS, this);
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        String jsonStr = new String(BufferUtils.toBytes(buf), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        RequestStatusMessage requestStatusMessage = gson.fromJson(jsonStr, RequestStatusMessage.class);

        ClientStatusMessage clientStatusMessage = new ClientStatusMessage();
        clientStatusMessage.setStatus(AutoStreamingAssistant.CAMERA.cameraPlayerUUID == null ? ClientStatus.READY : ClientStatus.BOUND);
        clientStatusMessage.setVersion(AutoStreamingAssistant.VERSION);
        String resultJson = gson.toJson(clientStatusMessage);

        responseSender.sendPacket(PacketID.CLIENT_STATUS, PacketByteBufs.create().writeBytes(resultJson.getBytes(StandardCharsets.UTF_8)));
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
