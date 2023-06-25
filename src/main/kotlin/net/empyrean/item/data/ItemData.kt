package net.empyrean.item.data

import net.empyrean.item.prefix.Prefix
import net.empyrean.item.prefix.PrefixSerializer
import net.minecraft.world.item.ItemStack

open class ItemData(stack: ItemStack) : ItemDataSchema(stack) {
    val obtainedAt by property("ObtainedAt", System.currentTimeMillis())
    var prefix by property<Prefix?>(PrefixSerializer, "Prefix", null)
}
