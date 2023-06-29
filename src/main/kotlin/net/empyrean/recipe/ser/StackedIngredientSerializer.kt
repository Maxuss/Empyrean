package net.empyrean.recipe.ser

import com.google.gson.JsonObject
import net.empyrean.EmpyreanMod
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

object StackedIngredientSerializer: CustomIngredientSerializer<StackedIngredient> {
    override fun getIdentifier(): ResourceLocation {
        return ResourceLocation(EmpyreanMod.modId, "stacked")
    }

    override fun read(json: JsonObject): StackedIngredient {
        val inner = Ingredient.fromJson(json["inner"])
        val count = GsonHelper.getAsInt(json, "count", 1)
        return StackedIngredient(inner, count)
    }

    override fun read(buf: FriendlyByteBuf): StackedIngredient {
        val inner = Ingredient.fromNetwork(buf)
        val count = buf.readVarInt()
        return StackedIngredient(inner, count)
    }

    override fun write(buf: FriendlyByteBuf, ingredient: StackedIngredient) {
        ingredient.inner.toNetwork(buf)
        buf.writeVarInt(ingredient.count)
    }

    override fun write(json: JsonObject, ingredient: StackedIngredient) {
        json.add("inner", ingredient.inner.toJson())
        json.addProperty("count", ingredient.count)
    }
}

class StackedIngredient(
    internal val inner: Ingredient,
    internal val count: Int,
): CustomIngredient {
    override fun test(stack: ItemStack): Boolean {
        return inner.test(stack) && stack.count >= count
    }

    override fun getMatchingStacks(): MutableList<ItemStack> {
        return inner.items.map { it.count = count; it }.toMutableList()
    }

    override fun requiresTesting(): Boolean {
        return inner.requiresTesting()
    }

    override fun getSerializer(): CustomIngredientSerializer<*> {
        return StackedIngredientSerializer
    }
}