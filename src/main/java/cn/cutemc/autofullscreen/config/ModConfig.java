package cn.cutemc.autofullscreen.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class ModConfig {

    public MainConfig mainConfig;

    public ModConfig() {
        AutoConfig.register(MainConfig.class, GsonConfigSerializer::new);
        mainConfig = AutoConfig.getConfigHolder(MainConfig.class).getConfig();
    }

}
