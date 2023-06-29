package net.empyrean.gui.screen.impl

import net.empyrean.menu.handlers.ACTMenuHandler
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.ImageButton
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ClickType
import net.minecraft.world.inventory.RecipeBookMenu
import net.minecraft.world.inventory.Slot

// pretty much just CraftingScreen
class ACTScreen(handler: ACTMenuHandler, inv: Inventory, title: Component): AbstractContainerScreen<ACTMenuHandler>(handler, inv, title), RecipeUpdateListener {
    private val CRAFTING_TABLE_LOCATION = ResourceLocation("textures/gui/container/crafting_table.png")
    private val RECIPE_BUTTON_LOCATION = ResourceLocation("textures/gui/recipe_button.png")
    private val recipeBookComponent = RecipeBookComponent()
    private var widthTooNarrow = false

    override fun init() {
        super.init()
        widthTooNarrow = width < 379
        recipeBookComponent.init(width, height, minecraft, widthTooNarrow, menu as RecipeBookMenu<*>)
        leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth)
        addRenderableWidget(ImageButton(
            leftPos + 5, height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION
        ) { button: Button ->
            recipeBookComponent.toggleVisibility()
            leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth)
            button.setPosition(leftPos + 5, height / 2 - 49)
        })
        addWidget(recipeBookComponent)
        setInitialFocus(recipeBookComponent)
        titleLabelX = 29
    }

    override fun containerTick() {
        super.containerTick()
        recipeBookComponent.tick()
    }

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        renderBackground(guiGraphics)
        if (recipeBookComponent.isVisible && widthTooNarrow) {
            renderBg(guiGraphics, f, i, j)
            recipeBookComponent.render(guiGraphics, i, j, f)
        } else {
            recipeBookComponent.render(guiGraphics, i, j, f)
            super.render(guiGraphics, i, j, f)
            recipeBookComponent.renderGhostRecipe(guiGraphics, leftPos, topPos, true, f)
        }
        renderTooltip(guiGraphics, i, j)
        recipeBookComponent.renderTooltip(guiGraphics, leftPos, topPos, i, j)
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
        return (!widthTooNarrow || !recipeBookComponent.isVisible) && super.isHovering(
            x,
            y,
            width,
            height,
            mouseX,
            mouseY
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            focused = recipeBookComponent
            return true
        }
        return if (widthTooNarrow && recipeBookComponent.isVisible) {
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
        val bl =
            mouseX < guiLeft.toDouble() || mouseY < guiTop.toDouble() || mouseX >= (guiLeft + imageWidth).toDouble() || mouseY >= (guiTop + imageHeight).toDouble()
        return recipeBookComponent.hasClickedOutside(
            mouseX, mouseY,
            leftPos, topPos, imageWidth, imageHeight, mouseButton
        ) && bl
    }

    override fun slotClicked(slot: Slot, slotId: Int, mouseButton: Int, type: ClickType) {
        super.slotClicked(slot, slotId, mouseButton, type)
        recipeBookComponent.slotClicked(slot)
    }

    override fun recipesUpdated() {
        recipeBookComponent.recipesUpdated()
    }

    override fun getRecipeBookComponent(): RecipeBookComponent {
        return recipeBookComponent
    }

}