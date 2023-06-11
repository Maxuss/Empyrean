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

    private final Option<java.lang.Integer> aValue = this.optionForKey(this.keys.aValue);
    private final Option<java.lang.String> regex = this.optionForKey(this.keys.regex);
    private final Option<java.lang.Boolean> nestingTime_togglee = this.optionForKey(this.keys.nestingTime_togglee);
    private final Option<java.lang.Boolean> nestingTime_yesThisIsAlsoNested = this.optionForKey(this.keys.nestingTime_yesThisIsAlsoNested);
    private final Option<java.lang.Byte> nestingTime_nestingTimeIntensifies_wowSoNested = this.optionForKey(this.keys.nestingTime_nestingTimeIntensifies_wowSoNested);
    private final Option<java.util.List<java.lang.Integer>> nestingTime_nestedIntegers = this.optionForKey(this.keys.nestingTime_nestedIntegers);
    private final Option<java.util.List<java.lang.String>> someOption = this.optionForKey(this.keys.someOption);
    private final Option<java.lang.Float> floting = this.optionForKey(this.keys.floting);
    private final Option<java.lang.String> thisIsAStringValue = this.optionForKey(this.keys.thisIsAStringValue);
    private final Option<java.util.List<java.lang.String>> thereAreStringsHere = this.optionForKey(this.keys.thereAreStringsHere);
    private final Option<net.empyrean.config.client.ClientConfigModel.WowValues> broTheresAnEnum = this.optionForKey(this.keys.broTheresAnEnum);
    private final Option<io.wispforest.owo.ui.core.Color> anEpicColor = this.optionForKey(this.keys.anEpicColor);
    private final Option<io.wispforest.owo.ui.core.Color> anEpicColorWithAlpha = this.optionForKey(this.keys.anEpicColorWithAlpha);
    private final Option<java.lang.String> noSeeingThis = this.optionForKey(this.keys.noSeeingThis);

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

    public int aValue() {
        return aValue.value();
    }

    public void aValue(int value) {
        aValue.set(value);
    }

    public java.lang.String regex() {
        return regex.value();
    }

    public void regex(java.lang.String value) {
        regex.set(value);
    }

    public final NestingTime nestingTime = new NestingTime();
    public class NestingTime implements Nested {
        public boolean togglee() {
            return nestingTime_togglee.value();
        }

        public void togglee(boolean value) {
            nestingTime_togglee.set(value);
        }

        public boolean yesThisIsAlsoNested() {
            return nestingTime_yesThisIsAlsoNested.value();
        }

        public void yesThisIsAlsoNested(boolean value) {
            nestingTime_yesThisIsAlsoNested.set(value);
        }

        public final NestingTimeIntensifies nestingTimeIntensifies = new NestingTimeIntensifies();
        public class NestingTimeIntensifies implements SuperNested {
            public byte wowSoNested() {
                return nestingTime_nestingTimeIntensifies_wowSoNested.value();
            }

            public void wowSoNested(byte value) {
                nestingTime_nestingTimeIntensifies_wowSoNested.set(value);
            }

        }
        public java.util.List<java.lang.Integer> nestedIntegers() {
            return nestingTime_nestedIntegers.value();
        }

        public void nestedIntegers(java.util.List<java.lang.Integer> value) {
            nestingTime_nestedIntegers.set(value);
        }

    }
    public java.util.List<java.lang.String> someOption() {
        return someOption.value();
    }

    public void someOption(java.util.List<java.lang.String> value) {
        someOption.set(value);
    }

    public float floting() {
        return floting.value();
    }

    public void floting(float value) {
        floting.set(value);
    }

    public java.lang.String thisIsAStringValue() {
        return thisIsAStringValue.value();
    }

    public void thisIsAStringValue(java.lang.String value) {
        thisIsAStringValue.set(value);
    }

    public java.util.List<java.lang.String> thereAreStringsHere() {
        return thereAreStringsHere.value();
    }

    public void thereAreStringsHere(java.util.List<java.lang.String> value) {
        thereAreStringsHere.set(value);
    }

    public net.empyrean.config.client.ClientConfigModel.WowValues broTheresAnEnum() {
        return broTheresAnEnum.value();
    }

    public void broTheresAnEnum(net.empyrean.config.client.ClientConfigModel.WowValues value) {
        broTheresAnEnum.set(value);
    }

    public io.wispforest.owo.ui.core.Color anEpicColor() {
        return anEpicColor.value();
    }

    public void anEpicColor(io.wispforest.owo.ui.core.Color value) {
        anEpicColor.set(value);
    }

    public io.wispforest.owo.ui.core.Color anEpicColorWithAlpha() {
        return anEpicColorWithAlpha.value();
    }

    public void anEpicColorWithAlpha(io.wispforest.owo.ui.core.Color value) {
        anEpicColorWithAlpha.set(value);
    }

    public java.lang.String noSeeingThis() {
        return noSeeingThis.value();
    }

    public void noSeeingThis(java.lang.String value) {
        noSeeingThis.set(value);
    }

    public interface Nested {
        boolean togglee();
        void togglee(boolean value);
        boolean yesThisIsAlsoNested();
        void yesThisIsAlsoNested(boolean value);
        java.util.List<java.lang.Integer> nestedIntegers();
        void nestedIntegers(java.util.List<java.lang.Integer> value);
    }
    public interface SuperNested {
        byte wowSoNested();
        void wowSoNested(byte value);
    }
    public static class Keys {
        public final Option.Key aValue = new Option.Key("aValue");
        public final Option.Key regex = new Option.Key("regex");
        public final Option.Key nestingTime_togglee = new Option.Key("nestingTime.togglee");
        public final Option.Key nestingTime_yesThisIsAlsoNested = new Option.Key("nestingTime.yesThisIsAlsoNested");
        public final Option.Key nestingTime_nestingTimeIntensifies_wowSoNested = new Option.Key("nestingTime.nestingTimeIntensifies.wowSoNested");
        public final Option.Key nestingTime_nestedIntegers = new Option.Key("nestingTime.nestedIntegers");
        public final Option.Key someOption = new Option.Key("someOption");
        public final Option.Key floting = new Option.Key("floting");
        public final Option.Key thisIsAStringValue = new Option.Key("thisIsAStringValue");
        public final Option.Key thereAreStringsHere = new Option.Key("thereAreStringsHere");
        public final Option.Key broTheresAnEnum = new Option.Key("broTheresAnEnum");
        public final Option.Key anEpicColor = new Option.Key("anEpicColor");
        public final Option.Key anEpicColorWithAlpha = new Option.Key("anEpicColorWithAlpha");
        public final Option.Key noSeeingThis = new Option.Key("noSeeingThis");
    }
}

