package net.empyrean.recipe

import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

interface AdvancedRecipe: Recipe<CraftingContainer> {
    override fun getType(): RecipeType<*> {
        return EmpyreanRecipes.AdvancedCrafting
    }

    fun advancedMatches(container: CraftingContainer, level: Level): Boolean
}