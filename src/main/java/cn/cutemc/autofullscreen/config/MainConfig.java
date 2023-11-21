package cn.cutemc.autofullscreen.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "autofullscreen")
public class MainConfig implements ConfigData {

    public boolean autoFullScreen = true;

    public String fullScreenMonitorName = "";

    public boolean keepMaximizing = true;

    public boolean keepFullScreen = true;
}
