package net.empyrean.item.data

import net.minecraft.world.item.ItemStack

open class ItemData(stack: ItemStack): ItemDataSchema(stack) {
    val obtainedAt by property("ObtainedAt", System.currentTimeMillis())
}
