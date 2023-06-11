package net.empyrean.config.client;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClientConfig extends ConfigWrapper<net.empyrean.config.client.ClientConfigModel> {

    public final Keys keys = new Keys();



    private ClientConfig() {
        super(net.empyrean.config.client.ClientConfigModel.class);
    }

    private ClientConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(net.empyrean.config.client.ClientConfigModel.class, janksonBuilder);
    }

    public static ClientConfig createAndLoad() {
        var wrapper = new ClientConfig();
        wrapper.load();
        return wrapper;
    }

    public static ClientConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new ClientConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }



    public static class Keys {

    }
}

