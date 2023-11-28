package cn.cutemc.autostreamingassistant.mixin;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
@Log4j2
public class MinecraftClientMixin {

    @Inject(method = "run()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Recorder;startTick()V", shift = At.Shift.AFTER))
    private void tickInject(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        long windowId = client.getWindow().getHandle();

        if (AutoStreamingAssistant.CONFIG.mainConfig.keepMaximizing && !AutoStreamingAssistant.isLinuxMint && GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_ICONIFIED) == 1) {
            // 如果窗口最小化了,非LinuxMint系统
            GLFW.glfwRestoreWindow(windowId);
        }

        if (AutoStreamingAssistant.CONFIG.mainConfig.keepFullScreen && !client.getWindow().isFullscreen()) {
            // 如果窗口不是全屏的
            client.getWindow().toggleFullscreen();
            client.options.getFullscreen().setValue(true);
        }
    }

    @Inject(method = "joinWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At(value = "HEAD"))
    private void joinWorldHeadInject(ClientWorld world, CallbackInfo ci) {
        AutoStreamingAssistant.worldStatus.setLoading(true);
    }

    @Inject(method = "joinWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At(value = "RETURN"))
    private void joinWorldReturnInject(ClientWorld world, CallbackInfo ci) {
        AutoStreamingAssistant.worldStatus.setLoading(false);
    }
}
