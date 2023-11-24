# AutoStreamingAssistant

一个Fabric Mod，使自动直播Minecraft变得更加简单，适用于Minecraft 1.20.2版本。

### Mod功能：

- 启动游戏时自动全屏
- 指定显示游戏窗口的监视器
- 保持窗口全屏
- 保持窗口最大化
- 禁用鼠标锁定

### 获取窗口名称：

#### 游戏中:

在游戏中运行这行命令:

    /autostreamingassistant listmonitor

#### Linux:

在终端中运行这个命令：

    shell$ xrandr -q

    Screen 0: minimum 8 x 8, current 1024 x 768, maximum 16384 x 16384
    VGA-0 connected primary 1024x768+0+0 (normal left inverted right x axis y axis) 0mm x 0mm
    ...

屏幕的名称是`VGA-0`。

#### 其他操作系统：

在java中运行这段代码

    PointerBuffer buffer = GLFW.glfwGetMonitors();

    for (int i = 0; i < buffer.capacity(); i++) {
        long pointer = buffer.get(i);
        String name = GLFW.glfwGetMonitorName(pointer1);
        
        log.info("Monitor #" + i + " Name: " + name);
    }

### 配置：

配置文件位于`.minecraft/config/autofullscreen.json`。

    {
        "autoFullScreen": true,
        "fullScreenMonitorName": "Generic PnP Monitor",
        "keepMaximizing": true,
        "keepFullScreen": true,
        "disableMouseLock": true
    }

或者你可以使用Mod的配置屏幕来修改配置。

### 系统特性：

#### Linux Mint:

因为Cinnamon对窗口最小化的处理方式与其他桌面环境不同，在Linux Mint上无法阻止用户手动最小化，只能阻止Cinnamon自动最小化。

## 注意事项:
- 此Mod依赖于Fabric API，Cloth-Conf
- 此Mod仅在Minecraft 1.20.2版本测试过，其他版本未经测试

## 许可证:
此项目使用GPLv3许可证，详情请参阅[LICENSE](LICENSE)文件。

---

### 支持我就帮我买杯咖啡吧！


<a href="https://afdian.net/a/lushangkan"><img src="https://s2.loli.net/2023/11/21/iAuWGhQz4gFpalV.jpg" alt="afdian" width="300"/></a>