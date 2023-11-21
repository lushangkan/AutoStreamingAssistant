package cn.cutemc.autofullscreen;

import cn.cutemc.autofullscreen.config.ModConfig;
import cn.cutemc.autofullscreen.utils.SystemUtils;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ClientModInitializer;

@Log4j2
public class AutoFullScreen implements ClientModInitializer {

    public static AutoFullScreen INSTANCE;
    public static ModConfig CONFIG;
    public static boolean isLinuxMint = false;
    public static String windowTitle = "";

    @Override
    public void onInitializeClient() {

        INSTANCE = this;

        log.info("Loading AutoFullScreen...");

        isLinuxMint = SystemUtils.isLinuxMint();

        log.info("Registering Listeners...");

        log.info("Loading Config...");
        CONFIG = new ModConfig();

        log.info("AutoFullScreen Loaded!");

    }

}
