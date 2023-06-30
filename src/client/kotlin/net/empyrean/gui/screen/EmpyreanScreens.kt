package net.empyrean.gui.screen

import net.empyrean.gui.screen.impl.AdvancedCraftingTableScreen
import net.empyrean.menu.EmpyreanMenu
import net.minecraft.client.gui.screens.MenuScreens

object EmpyreanScreens {
    val ADVANCED_CRAFTING_TABLE = MenuScreens.register(EmpyreanMenu.ADVANCED_CRAFTING_TABLE) { handler, inv, title ->
        AdvancedCraftingTableScreen(handler, inv, title)
    }
}