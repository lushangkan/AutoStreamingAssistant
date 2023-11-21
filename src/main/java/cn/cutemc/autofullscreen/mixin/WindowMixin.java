package cn.cutemc.autofullscreen.mixin;

import cn.cutemc.autofullscreen.AutoFullScreen;
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

    @Inject(method = "<init>(Lnet/minecraft/client/WindowEventHandler;Lnet/minecraft/client/util/MonitorTracker;Lnet/minecraft/client/WindowSettings;Ljava/lang/String;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", shift = At.Shift.BEFORE))
    private void createWindowInject(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        if (AutoFullScreen.CONFIG.mainConfig.keepMaximizing) {
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        }
    }

    @ModifyVariable(method = "onWindowFocusChanged(JZ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private boolean onWindowFocusChangedInject(boolean focused) {
        if (AutoFullScreen.CONFIG.mainConfig.keepMaximizing) {
            return true;
        }

        return focused;
    }

    @Inject(method = "<init>(Lnet/minecraft/client/WindowEventHandler;Lnet/minecraft/client/util/MonitorTracker;Lnet/minecraft/client/WindowSettings;Ljava/lang/String;Ljava/lang/String;)V", at = @At(value = "RETURN"))
    private void constructorInject(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        AutoFullScreen.windowTitle = title;
    }

    @Inject(method = "setTitle(Ljava/lang/String;)V", at = @At(value = "HEAD"))
    private void setTitleInject(String title, CallbackInfo ci) {
        AutoFullScreen.windowTitle = title;
    }

}
