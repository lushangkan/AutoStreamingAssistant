package cn.cutemc.autostreamingassistant.utils;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;

import java.util.Arrays;

public class CommandUtils {

    /**
     * 检测玩家是否有权限执行指令
     * @param command 指令，带"/"
     * @param source 玩家
     * @param networkHandler 网络处理器
     * @return 是否有权限
     */
    public static boolean hasPermissions(String command, CommandSource source, ClientPlayNetworkHandler networkHandler) {
        CommandDispatcher<CommandSource> dispatcher = networkHandler.getCommandDispatcher();
        return Arrays.stream(dispatcher.getAllUsage(dispatcher.getRoot(), source, true)).toList().contains(command);
    }
}
