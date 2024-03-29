package net.empyrean.item.impl.wings

import net.empyrean.item.rarity.ItemRarity
import net.empyrean.player.Stats
import net.fabricmc.fabric.api.item.v1.FabricItemSettings

class FledglingWings: EmpyreanWings(
    FabricItemSettings().maxCount(1),
    ItemRarity.RARE,
    WingProperties(
        40f, // ~40 blocks
        60f, // 60 ticks, ~3 seconds
        0.3f, // kinda slow
        0.4f,
        ascensionSpeed = 0.7f,
        descendSpeed = 1.5f
    ))

class AngelWings: EmpyreanWings(
    FabricItemSettings().maxCount(1),
    ItemRarity.EPIC,
    WingProperties(
        90f,
        300f,
        0.7f,
        0.6f,
        ascensionSpeed = 0.7f,
        descendSpeed = 2.5f,
        diveSpeed = 0.8f
    )
)

class InsaneWings: EmpyreanWings(
    FabricItemSettings().maxCount(1),
    ItemRarity.EXPERT,
    WingProperties(
        190f, // ~25 blocks
        450f, // 60 ticks, ~3 seconds
        1.9f, // kinda slow
        1.9f,
        ascensionSpeed = 2f,
        descendSpeed = 2f,
        diveSpeed = 1.6f
    )) {
    override val stats: Stats
        get() = Stats.of {
            maxHealth = 50f
            knockback = 1.2f
            acceleration = 2f
            healthRegen = 0.2f
            maxMana = 10f
            acceleration = 0.05f
        }
}