package cn.cutemc.autostreamingassistant.mixin;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "equipStack", at = @At("HEAD"), cancellable = true)
    public void equipStackInject(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.equals(client.player) || !player.getUuid().equals(AutoStreamingAssistant.CAMERA.cameraPlayerUUID)) return;

        ci.cancel();
    }

}
