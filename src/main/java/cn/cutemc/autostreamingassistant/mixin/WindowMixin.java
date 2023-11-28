package cn.cutemc.autostreamingassistant.mixin;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
@Log4j2
public class WindowMixin {

    @ModifyArg(method = "<init>(Lnet/minecraft/client/WindowEventHandler;Lnet/minecraft/client/util/MonitorTracker;Lnet/minecraft/client/WindowSettings;Ljava/lang/String;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/MonitorTracker;getMonitor(J)Lnet/minecraft/client/util/Monitor;"), index = 0)
    private long getMonitorInject(long defPointer) {

        long result = defPointer;

        if (!AutoStreamingAssistant.CONFIG.mainConfig.fullScreenMonitorName.equals("")) {
            // 如果配置文件中指定了全屏显示器的名称

            PointerBuffer buffer = GLFW.glfwGetMonitors();
            boolean changed = false;

            if (buffer != null) {
                for (int i = 0; i < buffer.capacity(); i++) {
                    long pointer1 = buffer.get(i);
                    String name = GLFW.glfwGetMonitorName(pointer1);

                    if (name != null && name.equals(AutoStreamingAssistant.CONFIG.mainConfig.fullScreenMonitorName)) {
                        log.info("Found monitor with name " + AutoStreamingAssistant.CONFIG.mainConfig.fullScreenMonitorName);
                        result = pointer1;
                        changed = true;
                        break;
                    }
                }

                if (!changed && !AutoStreamingAssistant.CONFIG.mainConfig.fullScreenMonitorName.equals("")) {
                    log.warn("Could not find monitor with name " + AutoStreamingAssistant.CONFIG.mainConfig.fullScreenMonitorName);
                    log.warn("Using primary monitor instead");
                }
            }
        }

        return result;
    }

    @Inject(method = "<init>(Lnet/minecraft/client/WindowEventHandler;Lnet/minecraft/client/util/MonitorTracker;Lnet/minecraft/client/WindowSettings;Ljava/lang/String;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", shift = At.Shift.BEFORE))
    private void createWindowInject(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        if (AutoStreamingAssistant.CONFIG.mainConfig.keepMaximizing) {
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        }
    }

    @ModifyVariable(method = "onWindowFocusChanged(JZ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private boolean onWindowFocusChangedInject(boolean focused) {
        if (AutoStreamingAssistant.CONFIG.mainConfig.keepMaximizing) {
            return true;
        }

        return focused;
    }

}
