package net.empyrean.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "empyrean")
public class ClientConfig implements ConfigData {
    public boolean doNotTouchEnablesSinglePlayer = false;
}
