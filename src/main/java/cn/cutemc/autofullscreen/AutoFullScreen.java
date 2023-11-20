package cn.cutemc.autofullscreen;

import cn.cutemc.autofullscreen.config.ModConfig;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ClientModInitializer;
import org.lwjgl.glfw.GLFW;

@Log4j2
public class AutoFullScreen implements ClientModInitializer {

    public static AutoFullScreen INSTANCE;
    public static ModConfig CONFIG;

    @Override
    public void onInitializeClient() {

        INSTANCE = this;

        log.info("Loading AutoFullScreen...");

        log.info("Loading Config...");
        CONFIG = new ModConfig();

        log.info("AutoFullScreen Loaded!");

    }

}
