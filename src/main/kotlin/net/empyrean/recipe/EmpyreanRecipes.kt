package net.empyrean.recipe

import net.minecraft.world.item.crafting.RecipeType

object EmpyreanRecipes {
    object AdvancedCrafting: RecipeType<AdvancedRecipe> {
        override fun toString(): String {
            return "advanced_crafting"
        }
    }
}