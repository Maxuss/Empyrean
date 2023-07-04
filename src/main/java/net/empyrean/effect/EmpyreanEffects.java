package net.empyrean.effect;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class EmpyreanEffects implements AutoRegistryContainer<MobEffect> {
    public static final EmpyreanEffect BOSS_PRESENCE = new BossPresenceEffect();

    @Override
    public Registry<MobEffect> getRegistry() {
        return BuiltInRegistries.MOB_EFFECT;
    }

    @Override
    public Class<MobEffect> getTargetFieldType() {
        return MobEffect.class;
    }
}
