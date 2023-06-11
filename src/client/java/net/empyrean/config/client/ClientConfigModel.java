package net.empyrean.config.client;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Sync;

@Sync(Option.SyncMode.INFORM_SERVER)
@Modmenu(modId = "empyrean")
@Config(name = "empyrean", wrapperName = "ClientConfig")
public class ClientConfigModel {
    public String enableDebugLogging;
}
