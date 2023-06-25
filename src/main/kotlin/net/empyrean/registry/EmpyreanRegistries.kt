package net.empyrean.registry

import net.empyrean.EmpyreanMod
import net.empyrean.item.prefix.Prefix
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.minecraft.core.MappedRegistry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

object EmpyreanRegistries {
    @JvmStatic
    val PREFIX: MappedRegistry<Prefix> = FabricRegistryBuilder.createSimple<Prefix>(ResourceKey.createRegistryKey(ResourceLocation(EmpyreanMod.modId, "prefix"))).buildAndRegister()
}