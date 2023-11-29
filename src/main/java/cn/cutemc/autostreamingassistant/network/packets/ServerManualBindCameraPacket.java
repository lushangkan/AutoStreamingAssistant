package cn.cutemc.autostreamingassistant.network.packets;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ServerManualBindCameraPacket {

    private UUID playerUuid;

}
