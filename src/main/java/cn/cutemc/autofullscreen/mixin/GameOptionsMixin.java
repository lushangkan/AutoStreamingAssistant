package cn.cutemc.autofullscreen.mixin;

import cn.cutemc.autofullscreen.AutoFullScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;Ljava/io/File;)V", at = @At("RETURN"))
    public void constructorInject(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        GameOptions option = (GameOptions) (Object) this;

        option.getFullscreen().setValue(AutoFullScreen.CONFIG.mainConfig.autoFullScreen);
    }
}
