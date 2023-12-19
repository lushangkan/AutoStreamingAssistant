package cn.cutemc.autostreamingassistant.network.packets;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import cn.cutemc.autostreamingassistant.camera.BindResult;
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

import java.beans.PropertyChangeSupport;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ClientBindCameraHandler implements ClientPlayNetworking.PlayChannelHandler {

    public ClientBindCameraHandler() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID.BIND_CAMERA, this);
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (client.world == null) return;

        Gson gson = new Gson();
        String jsonStr = buf.readString();

        BindCameraMessage message = gson.fromJson(jsonStr, BindCameraMessage.class);

        Runnable thread = () -> {
          Runnable bindCamera = () -> client.execute(() -> {
              BindResult result = AutoStreamingAssistant.CAMERA.bindCamera(message.getPlayerUuid());

              BindCameraResultMessage resultMessage = new BindCameraResultMessage();
              resultMessage.setSuccess(result == BindResult.SUCCESS);
              resultMessage.setResult(result);

              PacketByteBuf resultBuf = PacketByteBufs.create();
              resultBuf.writeBytes(gson.toJson(resultMessage).getBytes(StandardCharsets.UTF_8));

              responseSender.sendPacket(PacketID.BIND_CAMERA_RESULT, resultBuf);
          });

          if (client.world.getPlayers().stream().filter(player -> player.getUuid().equals(message.getPlayerUuid())).toList().size() != 1) {
              // 找不到玩家

              for (int i = 0; i < AutoStreamingAssistant.CONFIG.mainConfig.findPlayerTimeout; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (client.world.getPlayers().stream().filter(player -> player.getUuid().equals(message.getPlayerUuid())).toList().size() == 1) {
                        bindCamera.run();
                        return;
                    }
              }

              // 超时
              BindCameraResultMessage resultMessage = new BindCameraResultMessage();
              resultMessage.setSuccess(false);
              resultMessage.setResult(BindResult.NOT_FOUND_PLAYER);

              PacketByteBuf resultBuf = PacketByteBufs.create();
              resultBuf.writeBytes(gson.toJson(resultMessage).getBytes(StandardCharsets.UTF_8));

              responseSender.sendPacket(PacketID.BIND_CAMERA_RESULT, resultBuf);

              return;
          }

          bindCamera.run();
        };

        if (AutoStreamingAssistant.worldStatus.isLoading()) {
            // 加载世界中, 等待结束后再执行
            PropertyChangeSupport changeListener = new PropertyChangeSupport(AutoStreamingAssistant.worldStatus);
            changeListener.addPropertyChangeListener("loading", evt -> {
                if (!AutoStreamingAssistant.worldStatus.isLoading()) {
                    new Thread(thread).start();
                }
            });

            return;
        }

        new Thread(thread).start();
    }

    @Getter
    @Setter
    static class BindCameraMessage {
        private UUID playerUuid;
    }

    @Getter
    @Setter
    static class BindCameraResultMessage {
        private boolean success;
        private BindResult result;
    }

}
