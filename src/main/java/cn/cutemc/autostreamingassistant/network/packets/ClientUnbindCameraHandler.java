package cn.cutemc.autostreamingassistant.network.packets;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import cn.cutemc.autostreamingassistant.camera.UnbindResult;
import cn.cutemc.autostreamingassistant.network.PacketID;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.nio.charset.StandardCharsets;

public class ClientUnbindCameraHandler implements ClientPlayNetworking.PlayChannelHandler {

    public ClientUnbindCameraHandler() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID.UNBIND_CAMERA, this);
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        UnbindResult result = AutoStreamingAssistant.CAMERA.unbindCamera();

        Gson gson = new Gson();

        UnbindCameraResultMessage message = new UnbindCameraResultMessage();
        message.setSuccess(result == UnbindResult.SUCCESS);
        message.setResult(result);

        String jsonStr = gson.toJson(message);

        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeBytes(jsonStr.getBytes(StandardCharsets.UTF_8));

        responseSender.sendPacket(PacketID.UNBIND_CAMERA_RESULT, packetByteBuf);
    }

    @Getter
    @Setter
    static class UnbindCameraResultMessage {
        private boolean success;
        private UnbindResult result;
    }

}
