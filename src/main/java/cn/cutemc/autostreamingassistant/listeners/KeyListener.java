package cn.cutemc.autostreamingassistant.listeners;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import cn.cutemc.autostreamingassistant.config.MainConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class KeyListener implements ClientTickEvents.EndTick{

    public KeyListener() {
        ClientTickEvents.END_CLIENT_TICK.register(this);
    }

    @Override
    public void onEndTick(MinecraftClient client) {
        while (AutoStreamingAssistant.KEYBINDING.keyBinding.wasPressed()) {
            MainConfig mainConfig = AutoStreamingAssistant.CONFIG.mainConfig;

            mainConfig.disableMouseLock = !mainConfig.disableMouseLock;

            AutoStreamingAssistant.CONFIG.configHolder.save();
        }
    }
}
