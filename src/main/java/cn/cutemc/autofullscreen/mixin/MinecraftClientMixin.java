package cn.cutemc.autofullscreen.mixin;

import cn.cutemc.autofullscreen.AutoFullScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getFullscreen()Lnet/minecraft/client/option/SimpleOption;"))
    public SimpleOption<Boolean> getFullScreenOptionInjected(GameOptions instance) {
        instance.getFullscreen().setValue(AutoFullScreen.CONFIG.mainConfig.autoFullScreen);
        return instance.getFullscreen();
    }

}
