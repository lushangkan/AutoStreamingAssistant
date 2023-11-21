package cn.cutemc.autofullscreen.mixin;

import cn.cutemc.autofullscreen.AutoFullScreen;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "run()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Recorder;startTick()V", shift = At.Shift.AFTER))
    private void tickInject(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        long windowId = client.getWindow().getHandle();

        if (AutoFullScreen.CONFIG.mainConfig.keepMaximizing && GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_ICONIFIED) == 1) {
            // 如果窗口最小化了
            GLFW.glfwRestoreWindow(windowId);
        }

        if (AutoFullScreen.CONFIG.mainConfig.keepFullScreen && !client.getWindow().isFullscreen()) {
            // 如果窗口不是全屏的
            client.getWindow().toggleFullscreen();
            client.options.getFullscreen().setValue(true);
        }
    }
}
