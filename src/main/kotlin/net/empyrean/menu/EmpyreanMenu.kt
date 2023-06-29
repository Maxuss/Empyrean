package net.empyrean.menu

import net.empyrean.menu.handlers.ACTMenuHandler
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

object EmpyreanMenu {
    val ADVANCED_CRAFTING_TABLE: MenuType<ACTMenuHandler> =
        Registry.register(BuiltInRegistries.MENU, "empyrean:advanced_crafting_table", MenuType({ syncId, playerInv -> ACTMenuHandler(syncId, playerInv, ContainerLevelAccess.NULL) }, FeatureFlags.VANILLA_SET))
}