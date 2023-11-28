package cn.cutemc.autostreamingassistant.commands;

import cn.cutemc.autostreamingassistant.AutoStreamingAssistant;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class ModCommands implements ClientCommandRegistrationCallback {

    public ModCommands() {
        ClientCommandRegistrationCallback.EVENT.register(this);
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                ClientCommandManager.literal("autostreamingassistant").executes(this::mainCommand)
                        .then(ClientCommandManager.literal("help").executes(this::helpCommand))
                        .then(ClientCommandManager.literal("listmonitor").executes(this::listMonitorCommand))
                        .then(ClientCommandManager.literal("camera").executes(this::cameraCommand)
                                .then(ClientCommandManager.literal("bind").then(ClientCommandManager.argument("target", StringArgumentType.string()).suggests((context, builder) -> {
                                    ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
                                    if (clientPlayer == null) throw new RuntimeException("ClientPlayer is null!");
                                    return CommandSource.suggestMatching(clientPlayer.networkHandler.getPlayerList().stream().filter(player -> AutoStreamingAssistant.CAMERA.cameraPlayerUUID == null || !AutoStreamingAssistant.CAMERA.cameraPlayerUUID.equals(player.getProfile().getId())).map(player -> player.getProfile().getName()), builder);
                                }).executes(this::bindCameraCommand)))
                                .then(ClientCommandManager.literal("unbind").executes(this::unbindCameraCommand))
                        )
        );
    }

    public int mainCommand(CommandContext<FabricClientCommandSource> context) {
        return helpCommand(context);
    }

    public int helpCommand(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        source.sendFeedback(
                Text.translatable("commands.autostreamingassistant.help.title").append(": \n").styled(style -> style.withColor(TextColor.parse("gold")))
                        .append(Text.literal("● /autostreamingassistant help").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(" - ")).append(Text.translatable("commands.autostreamingassistant.help.help.description").append("\n"))
                        .append(Text.literal("● /autostreamingassistant listmonitor").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(" - ")).append(Text.translatable("commands.autostreamingassistant.help.listmonitor.description").append("\n"))
                        .append(Text.literal("● /autostreamingassistant camera bind").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(" - ")).append(Text.translatable("commands.autostreamingassistant.help.camera.bind.description").append("\n"))
                        .append(Text.literal("● /autostreamingassistant camera unbind").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(" - ")).append(Text.translatable("commands.autostreamingassistant.help.camera.unbind.description").append("\n"))
        );
        return 1;
    }

    public int listMonitorCommand(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();

        PointerBuffer buffer = GLFW.glfwGetMonitors();
        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        if (buffer == null) {
            source.sendFeedback(Text.translatable("commands.autostreamingassistant.listmonitor.notfound").styled(style -> style.withColor(TextColor.parse("red"))));
            return 1;
        }

        MutableText result = Text.translatable("commands.autostreamingassistant.listmonitor.title").append(":\n").styled(style -> style.withColor(TextColor.parse("gold")));

        for (int i = 0; i < buffer.capacity(); i++) {
            long monitorHandle = buffer.get(i);

            String name = GLFW.glfwGetMonitorName(monitorHandle);
            if (name == null) {
                name = Text.translatable("commands.autostreamingassistant.listmonitor.unknown").getString();
            }

            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitorHandle);

            int height = -1;
            int width = -1;

            if (videoMode != null) {
                height = videoMode.height();
                width = videoMode.width();
            }

            boolean isPrimary = monitorHandle == primaryMonitor;

            result.append(Text.literal("\n"));
            result.append(Text.translatable("commands.autostreamingassistant.listmonitor.monitor").append("#" + i + ": " + (isPrimary ? "(" + Text.translatable("commands.autostreamingassistant.listmonitor.primary").getString() + ")" : "") + "\n")).styled(style -> style.withColor(TextColor.parse("gold")));
            result.append(Text.translatable("commands.autostreamingassistant.listmonitor.name").append(": ").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(name)).append(Text.literal("\n"));
            result.append(Text.translatable("commands.autostreamingassistant.listmonitor.height").append(": ").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(height != -1 ? String.valueOf(height) : Text.translatable("commands.autostreamingassistant.listmonitor.height.error").getString())).append(Text.literal("\n"));
            result.append(Text.translatable("commands.autostreamingassistant.listmonitor.width").append(": ").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(width != -1 ? String.valueOf(width) : Text.translatable("commands.autostreamingassistant.listmonitor.width.error").getString()));

        }

        source.sendFeedback(result);

        return 1;
    }

    public int cameraCommand(CommandContext<FabricClientCommandSource> context) {
        return helpCommand(context);
    }

    public int bindCameraCommand(CommandContext<FabricClientCommandSource> context) {
        String targetName = StringArgumentType.getString(context, "target");

        switch (AutoStreamingAssistant.CAMERA.bindCamera(targetName)) {
            case NOT_FOUND_PLAYER -> context.getSource().sendFeedback(Text.translatable("commands.autostreamingassistant.camera.bind.notfound", targetName).styled(style -> style.withColor(TextColor.parse("red"))));
            case NOT_AT_NEAR_BY -> context.getSource().sendFeedback(Text.translatable("commands.autostreamingassistant.camera.bind.notatnearby", targetName).styled(style -> style.withColor(TextColor.parse("yellow"))));
            case SUCCESS -> context.getSource().sendFeedback(Text.translatable("commands.autostreamingassistant.camera.bind.bound", targetName).styled(style -> style.withColor(TextColor.parse("gold"))));

        }

        return 1;
    }

    public int unbindCameraCommand(CommandContext<FabricClientCommandSource> context) {
        switch (AutoStreamingAssistant.CAMERA.unbindCamera()) {
            case NOT_BOUND_CAMERA -> context.getSource().sendFeedback(Text.translatable("commands.autostreamingassistant.camera.unbind.notbound").styled(style -> style.withColor(TextColor.parse("red"))));
            case SUCCESS -> context.getSource().sendFeedback(Text.translatable("commands.autostreamingassistant.camera.unbind.unbound").styled(style -> style.withColor(TextColor.parse("gold"))));
        }

        return 1;
    }
}
