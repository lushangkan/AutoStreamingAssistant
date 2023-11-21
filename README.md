# AutoFullScreen


A Fabric Mod to make the game start up full screen on a given screen for Minecraft 1.20.2.

### Mod Functions:

- Automatic full screen when starting a game
- Specify the monitor on which to display the game window
- Keep window full screen
- Keep window maximized

### Get window name:

#### Linux:

Run this command in terminal:

    shell$ xrandr -q

    Screen 0: minimum 8 x 8, current 1024 x 768, maximum 16384 x 16384
    VGA-0 connected primary 1024x768+0+0 (normal left inverted right x axis y axis) 0mm x 0mm
    ...

The name of the screen is `VGA-0`.

#### Other OS:

Run this code in java

    PointerBuffer buffer = GLFW.glfwGetMonitors();

    for (int i = 0; i < buffer.capacity(); i++) {
        long pointer = buffer.get(i);
        String name = GLFW.glfwGetMonitorName(pointer1);
        
        log.info("Monitor #" + i + " Name: " + name);
    }

### Config:

The config file is located in `.minecraft/config/autofullscreen.json`.

    {
        "autoFullScreen": true,
        "fullScreenMonitorName": "Generic PnP Monitor",
        "keepMaximizing": true,
        "keepFullScreen": true
    }

Or you can use the mod's config screen to change the config.

---

### Support me and buy me a coffee!

<br>

<a href="https://afdian.net/a/lushangkan"><img src="https://s2.loli.net/2023/11/21/iAuWGhQz4gFpalV.jpg" alt="afdian" width="300"/></a>

