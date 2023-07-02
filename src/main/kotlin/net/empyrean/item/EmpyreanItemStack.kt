package net.empyrean.item

import net.empyrean.item.data.ItemData
import net.empyrean.item.prefix.Prefix

interface EmpyreanItemStack {
    val itemData: ItemData?

    fun setPrefix(newPrefix: Prefix)
}