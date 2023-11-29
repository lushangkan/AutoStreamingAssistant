package cn.cutemc.autostreamingassistant.network;

import net.minecraft.util.Identifier;

public class ModPacketID {

    public static final Identifier REQUEST_STATUS = new Identifier("autostreamingassistant", "request_status");
    public static final Identifier CLIENT_STATUS = new Identifier("autostreamingassistant", "client_status");
    public static final Identifier BIND_CAMERA = new Identifier("autostreamingassistant", "bind_camera");
    public static final Identifier UNBIND_CAMERA = new Identifier("autostreamingassistant", "unbind_camera");

    public static final Identifier BIND_CAMERA_RESULT = new Identifier("autostreamingassistant", "bind_camera_result");
    public static final Identifier UNBIND_CAMERA_RESULT = new Identifier("autostreamingassistant", "unbind_camera_result");
}
