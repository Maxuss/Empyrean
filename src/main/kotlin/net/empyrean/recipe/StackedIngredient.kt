package net.empyrean.recipe

import com.google.common.collect.Lists
import com.google.gson.JsonObject
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

class StackedItemValue(private val stack: ItemStack): Ingredient.Value {
    override fun getItems(): MutableCollection<ItemStack> {
        return Collections.singleton(stack)
    }

    override fun serialize(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.stack.item).toString())
        jsonObject.addProperty("count", stack.count)
        return jsonObject
    }
}

class StackedTagValue(private val tag: TagKey<Item>, private val count: Int): Ingredient.Value {
    override fun getItems(): Collection<ItemStack> {
        val list = Lists.newArrayList<ItemStack>()
        for (holder in BuiltInRegistries.ITEM.getTagOrEmpty(tag)) {
            list.add(ItemStack(holder))
        }
        return list
    }

    override fun serialize(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("tag", tag.location().toString())
        jsonObject.addProperty("count", count)
        return jsonObject
    }
}