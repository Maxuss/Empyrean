package net.empyrean.interop.rei

import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import me.shedaniel.rei.plugin.client.categories.crafting.DefaultCraftingCategory
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultShapedDisplay
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultShapelessDisplay
import net.empyrean.EmpyreanModClient
import net.empyrean.block.EmpyreanBlocks
import net.empyrean.recipe.AdvancedShapedRecipe
import net.empyrean.recipe.AdvancedShapelessRecipe
import net.minecraft.network.chat.Component

object Displays {
    @JvmField
    val ADVANCED_CRAFTING = CategoryIdentifier.of<DefaultCraftingDisplay<*>>(EmpyreanModClient.MODID, "advanced_crafting")
}

class AdvancedShapedCraftingDisplay(recipe: AdvancedShapedRecipe): DefaultShapedDisplay(recipe) {
    override fun getCategoryIdentifier(): CategoryIdentifier<*> {
        return Displays.ADVANCED_CRAFTING
    }
}
class AdvancedShapelessCraftingDisplay(recipe: AdvancedShapelessRecipe): DefaultShapelessDisplay(recipe) {
    override fun getCategoryIdentifier(): CategoryIdentifier<*> {
        return Displays.ADVANCED_CRAFTING
    }
}

class AdvancedCraftingCategory: DefaultCraftingCategory() {
    override fun getCategoryIdentifier(): CategoryIdentifier<out DefaultCraftingDisplay<*>> {
        return Displays.ADVANCED_CRAFTING
    }

    override fun getTitle(): Component {
        return Component.translatable("rei.empyrean.advanced_crafting.title")
    }

    override fun getIcon(): Renderer {
        return EntryStacks.of(EmpyreanBlocks.ADVANCED_CRAFTING_TABLE)
    }
}