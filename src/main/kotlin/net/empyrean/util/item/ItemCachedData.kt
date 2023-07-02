package net.empyrean.util.item

import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.player.Stats

@JvmRecord
data class ItemCachedData(
    val kind: ItemKind,
    val rarity: ItemRarity,
    val stats: Stats
)