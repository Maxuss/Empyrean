package net.empyrean.tag

import net.empyrean.EmpyreanMod
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

object EmpyreanTags {
    @JvmField
    val VOLATILE = TagKey.create(Registries.ITEM, ResourceLocation(EmpyreanMod.modId, "volatile"))
}