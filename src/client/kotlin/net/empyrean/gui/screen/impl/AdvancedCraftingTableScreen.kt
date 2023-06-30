package net.empyrean.gui.screen.impl

import net.empyrean.menu.handlers.AdvancedCraftingTableMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

// pretty much just CraftingScreen
class AdvancedCraftingTableScreen(handler: AdvancedCraftingTableMenu, inv: Inventory, title: Component): AbstractContainerScreen<AdvancedCraftingTableMenu>(handler, inv, title) {
    private var widthTooNarrow = false

    override fun init() {
        super.init()
        widthTooNarrow = width < 379
        leftPos = (width - imageWidth) / 2
        titleLabelX = 29
    }

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        renderBackground(guiGraphics)
        if (widthTooNarrow) {
            renderBg(guiGraphics, f, i, j)
        } else {
            super.render(guiGraphics, i, j, f)
        }
        renderTooltip(guiGraphics, i, j)
    }

    override fun renderBg(guiGraphics: GuiGraphics, f: Float, i: Int, j: Int) {
        val k = leftPos
        val l = (height - imageHeight) / 2
        guiGraphics.blit(CRAFTING_TABLE_LOCATION, k, l, 0, 0, imageWidth, imageHeight)
    }

    override fun isHovering(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        mouseX: Double,
        mouseY: Double
    ): Boolean {
        return !widthTooNarrow && super.isHovering(
            x,
            y,
            width,
            height,
            mouseX,
            mouseY
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        return if (widthTooNarrow) {
            true
        } else super.mouseClicked(mouseX, mouseY, button)
    }

    override fun hasClickedOutside(
        mouseX: Double,
        mouseY: Double,
        guiLeft: Int,
        guiTop: Int,
        mouseButton: Int
    ): Boolean {
        return mouseX < guiLeft.toDouble() || mouseY < guiTop.toDouble() || mouseX >= (guiLeft + imageWidth).toDouble() || mouseY >= (guiTop + imageHeight).toDouble()
    }

    companion object {
        private val CRAFTING_TABLE_LOCATION = ResourceLocation("textures/gui/container/crafting_table.png")
    }
}