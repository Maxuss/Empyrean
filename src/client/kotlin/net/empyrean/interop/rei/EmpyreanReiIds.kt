package net.empyrean.interop.rei

import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay
import net.empyrean.EmpyreanModClient

object EmpyreanReiIds {
    @JvmField
    val ADVANCED_CRAFTING: CategoryIdentifier<DefaultCraftingDisplay<*>> = CategoryIdentifier.of(EmpyreanModClient.MODID, "advanced_crafting")
}