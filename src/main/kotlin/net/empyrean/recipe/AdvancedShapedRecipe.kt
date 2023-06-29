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

class AdvancedShapedRecipe(
    id: ResourceLocation,
    group: String,
    category: CraftingBookCategory,
    width: Int,
    height: Int,
    recipeItems: NonNullList<Ingredient>,
    resultItem: ItemStack
): ShapedRecipe(id, group, category, width, height, recipeItems, resultItem), AdvancedRecipe {
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

    object Serializer: ShapedRecipe.Serializer() {
        override fun fromJson(recipeId: ResourceLocation, json: JsonObject): ShapedRecipe {
            val it = super.fromJson(recipeId, json)
            return AdvancedShapedRecipe(it.id, it.group, it.category(), it.width, it.height, it.ingredients, it.getResultItem(null))
        }

        override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): ShapedRecipe {
            val it = super.fromNetwork(recipeId, buffer)
            return AdvancedShapedRecipe(it.id, it.group, it.category(), it.width, it.height, it.ingredients, it.getResultItem(null))
        }
    }
}