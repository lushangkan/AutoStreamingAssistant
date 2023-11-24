package cn.cutemc.autofullscreen.listeners;

import cn.cutemc.autofullscreen.config.MainConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.event.ConfigSerializeEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.util.ActionResult;

public class ConfigListener implements ConfigSerializeEvent.Load<MainConfig>, ConfigSerializeEvent.Save<MainConfig> {

    @Override
    public ActionResult onLoad(ConfigHolder<MainConfig> configHolder, MainConfig mainConfig) {
        Mouse mouse = MinecraftClient.getInstance().mouse;
        if (!mouse.isCursorLocked() && !mainConfig.disableMouseLock) mouse.lockCursor();
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult onSave(ConfigHolder<MainConfig> configHolder, MainConfig mainConfig) {
        Mouse mouse = MinecraftClient.getInstance().mouse;
        if (!mouse.isCursorLocked() && !mainConfig.disableMouseLock) mouse.lockCursor();
        return ActionResult.SUCCESS;
    }

}
