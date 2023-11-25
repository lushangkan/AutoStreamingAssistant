package cn.cutemc.autostreamingassistant.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
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

        );
        return 1;
    }

    public int listMonitorCommand(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();

        PointerBuffer buffer = GLFW.glfwGetMonitors();
        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        if (buffer == null) {
            source.sendFeedback(Text.translatable("commands.autostreamingassistant.help.listmonitor.notfound").styled(style -> style.withColor(TextColor.parse("red"))));
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
}
