package net.empyrean.sound;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.empyrean.EmpyreanMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class EmpyreanSounds implements AutoRegistryContainer<SoundEvent> {
    public static final SoundEvent ADRENALINE_ACTIVATE = SoundEvent.createVariableRangeEvent(new ResourceLocation(EmpyreanMod.modId, "adrenaline_activate"));
    public static final SoundEvent ADRENALINE_FULL = SoundEvent.createVariableRangeEvent(new ResourceLocation(EmpyreanMod.modId, "adrenaline_full"));

    @Override
    public Registry<SoundEvent> getRegistry() {
        return BuiltInRegistries.SOUND_EVENT;
    }

    @Override
    public Class<SoundEvent> getTargetFieldType() {
        return SoundEvent.class;
    }
}
