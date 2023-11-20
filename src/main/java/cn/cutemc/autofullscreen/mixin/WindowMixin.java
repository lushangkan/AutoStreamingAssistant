package cn.cutemc.autofullscreen.mixin;

import cn.cutemc.autofullscreen.AutoFullScreen;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.RunArgs;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.Window;
import org.lwjgl.PointerBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.util.WindowProvider;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.concurrent.atomic.AtomicLong;

@Mixin(Window.class)
@Log4j2
public class WindowMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/MonitorTracker;getMonitor(J)Lnet/minecraft/client/util/Monitor;"), index = 0)
    public long getMonitorInject(long defPointer) {

        long result = defPointer;

        if (!AutoFullScreen.CONFIG.mainConfig.fullScreenMonitorName.equals("")) {
            // 如果配置文件中指定了全屏显示器的名称

            PointerBuffer buffer = GLFW.glfwGetMonitors();
            boolean changed = false;

            if (buffer != null) {
                for (int i = 0; i < buffer.capacity(); i++) {
                    long pointer1 = buffer.get(i);
                    String name = GLFW.glfwGetMonitorName(pointer1);

                    if (name != null && name.equals(AutoFullScreen.CONFIG.mainConfig.fullScreenMonitorName)) {
                        log.info("Found monitor with name " + AutoFullScreen.CONFIG.mainConfig.fullScreenMonitorName);
                        result = pointer1;
                        changed = true;
                        break;
                    }
                }

                if (!changed && !AutoFullScreen.CONFIG.mainConfig.fullScreenMonitorName.equals("")) {
                    log.warn("Could not find monitor with name " + AutoFullScreen.CONFIG.mainConfig.fullScreenMonitorName);
                    log.warn("Using primary monitor instead");
                }
            }
        }

        return result;
    }
}
