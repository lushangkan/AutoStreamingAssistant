package cn.cutemc.autostreamingassistant;

import cn.cutemc.autostreamingassistant.beans.WorldStatus;
import cn.cutemc.autostreamingassistant.camera.Camera;
import cn.cutemc.autostreamingassistant.commands.ModCommands;
import cn.cutemc.autostreamingassistant.config.ModConfig;
import cn.cutemc.autostreamingassistant.keybindings.ModKeyBinding;
import cn.cutemc.autostreamingassistant.listeners.ConfigListener;
import cn.cutemc.autostreamingassistant.listeners.KeyListener;
import cn.cutemc.autostreamingassistant.network.packets.ClientBindCameraHandler;
import cn.cutemc.autostreamingassistant.network.packets.ClientUnbindCameraHandler;
import cn.cutemc.autostreamingassistant.utils.SystemUtils;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ClientModInitializer;

@Log4j2
public class AutoStreamingAssistant implements ClientModInitializer {

    public static AutoStreamingAssistant INSTANCE;
    public static ModConfig CONFIG;
    public static ModKeyBinding KEYBINDING;
    public static Camera CAMERA;

    public static boolean isLinuxMint = false;
    public static WorldStatus worldStatus = new WorldStatus();

    @Override
    public void onInitializeClient() {

        INSTANCE = this;

        log.info("Loading AutoStreamingAssistant...");

        isLinuxMint = SystemUtils.isLinuxMint();

        log.info("Registering KeyBindings...");
        KEYBINDING = new ModKeyBinding();

        log.info("Registering Listeners...");
        new ConfigListener();
        new KeyListener();

        log.info("Loading Config...");
        CONFIG = new ModConfig();

        log.info("Registering Commands...");
        new ModCommands();

        log.info("Creating Camera...");
        CAMERA = new Camera();

        log.info("Initializing Network Listener...");
        new ClientBindCameraHandler();
        new ClientUnbindCameraHandler();

        log.info("AutoStreamingAssistant Loaded!");

    }

}
