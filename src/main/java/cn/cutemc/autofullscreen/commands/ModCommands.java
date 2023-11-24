package cn.cutemc.autofullscreen.commands;

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
                ClientCommandManager.literal("autofullscreen").executes(this::mainCommand)
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
                Text.translatable("commands.autofullscreen.help.title").append(": \n").styled(style -> style.withColor(TextColor.parse("gold")))
                        .append(Text.literal("● /autofullscreen help").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(" - ")).append(Text.translatable("commands.autofullscreen.help.help.description").append("\n"))
                        .append(Text.literal("● /autofullscreen listmonitor").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(" - ")).append(Text.translatable("commands.autofullscreen.help.listmonitor.description").append("\n"))

        );
        return 1;
    }

    public int listMonitorCommand(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();

        PointerBuffer buffer = GLFW.glfwGetMonitors();
        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        if (buffer == null) {
            source.sendFeedback(Text.translatable("commands.autofullscreen.help.listmonitor.notfound").styled(style -> style.withColor(TextColor.parse("red"))));
            return 1;
        }

        MutableText result = Text.translatable("commands.autofullscreen.listmonitor.title").append(":\n").styled(style -> style.withColor(TextColor.parse("gold")));

        for (int i = 0; i < buffer.capacity(); i++) {
            long monitorHandle = buffer.get(i);

            String name = GLFW.glfwGetMonitorName(monitorHandle);
            if (name == null) {
                name = Text.translatable("commands.autofullscreen.listmonitor.unknown").getString();
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
            result.append(Text.translatable("commands.autofullscreen.listmonitor.monitor").append("#" + i + ": " + (isPrimary ? "(" + Text.translatable("commands.autofullscreen.listmonitor.primary").getString() + ")" : "") + "\n")).styled(style -> style.withColor(TextColor.parse("gold")));
            result.append(Text.translatable("commands.autofullscreen.listmonitor.name").append(": ").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(name)).append(Text.literal("\n"));
            result.append(Text.translatable("commands.autofullscreen.listmonitor.height").append(": ").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(height != -1 ? String.valueOf(height) : Text.translatable("commands.autofullscreen.listmonitor.height.error").getString())).append(Text.literal("\n"));
            result.append(Text.translatable("commands.autofullscreen.listmonitor.width").append(": ").styled(style -> style.withColor(TextColor.parse("dark_aqua")))).append(Text.literal(width != -1 ? String.valueOf(width) : Text.translatable("commands.autofullscreen.listmonitor.width.error").getString()));

        }

        source.sendFeedback(result);

        return 1;
    }
}
