package net.empyrean.item.impl.wings

import net.empyrean.item.rarity.ItemRarity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings

class FledglingWings: EmpyreanWings(
    FabricItemSettings().maxCount(1),
    ItemRarity.RARE,
    WingProperties(
        40f, // ~25 blocks
        60f, // 60 ticks, 3 seconds
        0.3f, // kinda slow
        0.4f,
        ascensionSpeed = 0.7f,
        descendSpeed = 1.5f
    ))