package cn.cutemc.autostreamingassistant.keybindings;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinding {

    public KeyBinding keyBinding;

    public ModKeyBinding() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autostreamingassistant.togglelockmouse",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "key.autostreamingassistant.category"
        ));
    }
}
