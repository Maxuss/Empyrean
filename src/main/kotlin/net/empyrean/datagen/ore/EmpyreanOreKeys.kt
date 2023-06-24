package net.empyrean.datagen.ore

import net.empyrean.EmpyreanMod
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

object EmpyreanOreKeys {
    val GEYSERITE_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation(EmpyreanMod.modId, "geyserite_ore"))

    val PLACED_GEYSERITE = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation(EmpyreanMod.modId, "geyserite_ore"))
}