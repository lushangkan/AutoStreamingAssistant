package cn.cutemc.autostreamingassistant.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "autostreamingassistant")
public class MainConfig implements ConfigData {

    public boolean autoFullScreen = true;

    public String fullScreenMonitorName = "";

    public boolean keepMaximizing = true;

    public boolean keepFullScreen = true;

    public boolean disableMouseLock = true;

}
