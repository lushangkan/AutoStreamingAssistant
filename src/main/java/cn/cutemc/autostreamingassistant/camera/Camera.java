package cn.cutemc.autostreamingassistant.camera;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.UUID;

public class Camera {

    public UUID cameraPlayerUUID = null;
    public MinecraftClient client;

    public Camera() {
        client = MinecraftClient.getInstance();
    }

    public void bindCamera(PlayerEntity target) {
        clearPlayerInventory(target);

        client.setCameraEntity(target);

        cameraPlayerUUID = target.getUuid();
    }

    public BindResult bindCamera(String targetName) {
        if (client.world == null) return BindResult.WORLD_IS_NULL;

        ClientPlayerEntity player = client.player;

        if (player == null) return BindResult.PLAYER_IS_NULL;

        List<PlayerListEntry> targetList = player.networkHandler.getPlayerList().stream().filter(playerListEntry -> playerListEntry.getProfile().getName().equals(targetName)).toList();

        if (targetList.size() != 1) {
            return BindResult.NOT_FOUND_PLAYER;
        }

        PlayerEntity target = client.world.getPlayerByUuid(targetList.get(0).getProfile().getId());

        if (target == null) {
            return BindResult.NOT_AT_NEAR_BY;
        }

        bindCamera(target);

        return BindResult.SUCCESS;
    }

    public BindResult bindCamera(UUID targetUUID) {
        if (client.world == null) return BindResult.WORLD_IS_NULL;

        ClientPlayerEntity player = client.player;

        if (player == null) return BindResult.PLAYER_IS_NULL;

        List<PlayerListEntry> targetList = player.networkHandler.getPlayerList().stream().filter(playerListEntry -> playerListEntry.getProfile().getId().equals(targetUUID)).toList();

        if (targetList.size() != 1) {
            return BindResult.NOT_FOUND_PLAYER;
        }

        PlayerEntity target = client.world.getPlayerByUuid(targetList.get(0).getProfile().getId());

        if (target == null) {
            return BindResult.NOT_AT_NEAR_BY;
        }

        bindCamera(target);

        return BindResult.SUCCESS;
    }

    public UnbindResult unbindCamera() {
        if (cameraPlayerUUID == null) return UnbindResult.NOT_BOUND_CAMERA;

        client.setCameraEntity(client.player);

        cameraPlayerUUID = null;

        return UnbindResult.SUCCESS;
    }

    public void clearPlayerInventory(PlayerEntity player) {
        player.getInventory().clear();
    }
}
