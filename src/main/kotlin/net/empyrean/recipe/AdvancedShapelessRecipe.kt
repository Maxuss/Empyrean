package net.empyrean.recipe

import com.google.gson.JsonObject
import net.empyrean.menu.container.AdvancedCraftingContainer
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class AdvancedShapelessRecipe(
    id: ResourceLocation,
    group: String,
    category: CraftingBookCategory,
    resultItem: ItemStack,
    ingredients: NonNullList<Ingredient>
): ShapelessRecipe(id, group, category, resultItem, ingredients), AdvancedRecipe {
    override fun getType(): RecipeType<*> {
        return EmpyreanRecipes.AdvancedCrafting
    }

    override fun advancedMatches(container: CraftingContainer, level: Level): Boolean {
        return super.matches(container, level)
    }

    override fun matches(inv: CraftingContainer, level: Level): Boolean {
        if(inv is AdvancedCraftingContainer)
            return advancedMatches(inv, level)
        return false
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return Serializer
    }

    object Serializer: ShapelessRecipe.Serializer() {
        override fun fromJson(recipeId: ResourceLocation, json: JsonObject): ShapelessRecipe {
            val it = super.fromJson(recipeId, json)
            return AdvancedShapelessRecipe(it.id, it.group, it.category(), it.getResultItem(null), it.ingredients)
        }

        override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): ShapelessRecipe {
            val it = super.fromNetwork(recipeId, buffer)
            return AdvancedShapelessRecipe(it.id, it.group, it.category(), it.getResultItem(null), it.ingredients)
        }
    }
}