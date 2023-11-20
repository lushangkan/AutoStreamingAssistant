package cn.cutemc.autofullscreen.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "autofullscreen")
public class MainConfig implements ConfigData {

    @Comment(value = "Automatically switch to fullscreen when the game starts")
    public boolean autoFullScreen = true;

    @ConfigEntry.Gui.PrefixText()
    @Comment(value = "The name of the monitor to use for fullscreen. Leave blank to use the primary monitor")
    public String fullScreenMonitorName = "";

}
