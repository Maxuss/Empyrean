package net.empyrean.gui.screen

import net.empyrean.gui.screen.impl.ACTScreen
import net.empyrean.menu.EmpyreanMenu
import net.minecraft.client.gui.screens.MenuScreens

object EmpyreanScreens {
    val BOX_SCREEN = MenuScreens.register(EmpyreanMenu.ADVANCED_CRAFTING_TABLE) { handler, inv, title ->
        ACTScreen(handler, inv, title)
    }
}