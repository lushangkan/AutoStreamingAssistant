package cn.cutemc.autostreamingassistant.utils;

import lombok.SneakyThrows;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class SystemUtils {

    @SneakyThrows
    public static boolean isLinuxMint() {
        if (!System.getProperty("os.name").equals("Linux")) return false;

        DefaultExecutor executor = new DefaultExecutor();

        CommandLine cmdLine = CommandLine.parse("lsb_release -i");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        executor.setStreamHandler(new PumpStreamHandler(stream));

        int resultCode = executor.execute(cmdLine);

        if (resultCode != 0) return false;

        String resultStr = stream.toString(StandardCharsets.UTF_8);

        return resultStr.toLowerCase().replaceAll(" ", "").contains("linuxmint");
    }

}
