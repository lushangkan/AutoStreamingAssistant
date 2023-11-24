package cn.cutemc.autofullscreen.config;

import cn.cutemc.autofullscreen.listeners.ConfigListener;
import lombok.extern.log4j.Log4j2;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Log4j2
public class ModConfig {

    public MainConfig mainConfig;

    public ModConfig() {
        AutoConfig.register(MainConfig.class, GsonConfigSerializer::new);

        ConfigHolder<MainConfig> configHolder = AutoConfig.getConfigHolder(MainConfig.class);

        mainConfig = configHolder.getConfig();

        log.info("Registering Config Listeners...");
        ConfigListener configListener = new ConfigListener();
        configHolder.registerLoadListener(configListener);
        configHolder.registerSaveListener(configListener);
    }

}
