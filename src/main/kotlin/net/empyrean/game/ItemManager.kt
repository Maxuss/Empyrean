package net.empyrean.game

import com.google.common.collect.ImmutableMap
import net.empyrean.item.EmpyreanItem
import net.empyrean.item.EmpyreanItemStack
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.player.PlayerStat
import net.empyrean.player.Stats
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.*

object ItemManager {
    private val vanillaHardcodedStatTable: ImmutableMap<Item, Stats> = ImmutableMap.copyOf(mapOf(
        Items.BOW to Stats.of {
            damageRanged = 12
            attackSpeed = .2f
        },
        Items.CROSSBOW to Stats.of {
            damageRanged = 25
            attackSpeed = .1f
        },
        Items.TRIDENT to Stats.of {
            damageRogue = 40
            attackSpeed = .1f
            critChance = .15f
        },
        Items.DIAMOND_CHESTPLATE to Stats.of {
            damageReduction = .1f
            defense = 8
        },
        Items.DIAMOND_LEGGINGS to Stats.of {
            damageReduction = .08f
            defense = 6
        },
        Items.NETHERITE_CHESTPLATE to Stats.of {
            damageReduction = .2f
            defense = 12
        },
        Items.NETHERITE_LEGGINGS to Stats.of {
            damageReduction = .17f
            defense = 10
        } // TODO: maybe add elytra here too?
    ))

    private val vanillaHardcodedItemRarities: ImmutableMap<Item, ItemRarity> = ImmutableMap.copyOf(mapOf(
        Items.CROSSBOW to ItemRarity.UNCOMMON,
        Items.BLAZE_ROD to ItemRarity.UNCOMMON,
        Items.BLAZE_POWDER to ItemRarity.UNCOMMON,
        Items.TRIDENT to ItemRarity.UNCOMMON,
        Items.TURTLE_HELMET to ItemRarity.UNCOMMON,
        Items.TIPPED_ARROW to ItemRarity.UNCOMMON,
        Items.SPECTRAL_ARROW to ItemRarity.UNCOMMON,
        Items.CONDUIT to ItemRarity.EPIC,
        Items.GOLDEN_APPLE to ItemRarity.UNCOMMON,
        Items.ENCHANTED_GOLDEN_APPLE to ItemRarity.RARE,
        Items.POTION to ItemRarity.UNCOMMON,
        Items.SPLASH_POTION to ItemRarity.UNCOMMON,
        Items.LINGERING_POTION to ItemRarity.RARE,
        Items.ECHO_SHARD to ItemRarity.RARE
    ))

    private val vanillaHardcodedItemKinds: ImmutableMap<Item, ItemKind> = ImmutableMap.copyOf(mapOf(
        Items.TRIDENT to ItemKind.SWORD,
        Items.ELYTRA to ItemKind.WINGS,
        // TODO: more item kinds
    ))

    @JvmStatic
    fun extractItemStats(item: ItemStack): Stats {
        if(item.isEmpty)
            return Stats.empty()
        if(item.item is EmpyreanItem) {
            val empyrean = (item.item as EmpyreanItem)
            return empyrean.calculateStats(item)
        }
        @Suppress("CAST_NEVER_SUCCEEDS")
        val empyreanStack = item as EmpyreanItemStack
        val baseStats = tryExtractRawVanillaStats(item.item)
        val itemData = empyreanStack.itemData
        if(itemData != null) {
            val pfx = itemData.prefix ?: return baseStats
            val rarity = tryExtractVanillaRarity(item.item)
            val pfxStats = pfx.baseStats * ((1f + pfx.rarityMultiplier) * rarity.pfxModifier)
            return baseStats.merge(pfxStats)
        }
        return baseStats
    }

    @JvmStatic
    fun tryExtractVanillaRarity(item: Item): ItemRarity {
        if(item.descriptionId.contains("diamond"))
            return ItemRarity.UNCOMMON

        if(item.descriptionId.contains("netherite"))
            return ItemRarity.RARE

        return vanillaHardcodedItemRarities[item] ?: ItemRarity.COMMON
    }

    @JvmStatic
    fun tryExtractVanillaItemKind(item: Item): ItemKind {
        val split = BuiltInRegistries.ITEM.getKey(item).path.split("_").last()
        return try {
            ItemKind.valueOf(split.uppercase())
        } catch(e: IllegalArgumentException) {
            vanillaHardcodedItemKinds[item] ?: ItemKind.MATERIAL
        }
    }

    private fun tryExtractRawVanillaStats(item: Item): Stats {
        if(item in vanillaHardcodedStatTable.keys)
            return vanillaHardcodedStatTable[item]!!

        val baseStats = Stats.empty()
        when (item) {
            is SwordItem -> baseStats[PlayerStat.DAMAGE_MELEE] = item.damage * (if(item.descriptionId.contains("netherite")) 4 else if(item.descriptionId.contains("diamond")) 3 else if(item.descriptionId.contains("iron")) 2 else 1)
            is DiggerItem -> baseStats[PlayerStat.DAMAGE_MELEE] = item.attackDamage
            is ArmorItem -> baseStats[PlayerStat.DEFENSE] = item.defense.toFloat()
        }
        return baseStats
    }
}