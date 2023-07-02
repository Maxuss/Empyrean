package net.empyrean.item.data

import net.minecraft.world.item.ItemStack

class VanillaItemData(stack: ItemStack): ItemData(stack) {
    var isVanilla by property("IsVanilla", true)
}