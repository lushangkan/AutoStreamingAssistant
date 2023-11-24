package cn.cutemc.autofullscreen.mixin;

import cn.cutemc.autofullscreen.AutoFullScreen;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "lockCursor()V", at = @At("HEAD"), cancellable = true)
    public void lockCursorInject(CallbackInfo ci) {
        if (AutoFullScreen.CONFIG.mainConfig.disableMouseLock) ci.cancel();
    }

    @Inject(method = "unlockCursor()V", at = @At("HEAD"), cancellable = true)
    public void unlockCursorInject(CallbackInfo ci) {
        if (AutoFullScreen.CONFIG.mainConfig.disableMouseLock) ci.cancel();
    }

}
