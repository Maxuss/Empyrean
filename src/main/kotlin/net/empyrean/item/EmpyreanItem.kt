@file:Suppress("CAST_NEVER_SUCCEEDS") // it does, you dummy

package net.empyrean.item

import net.empyrean.chat.withColor
import net.empyrean.events.EmpyreanTooltipEvent
import net.empyrean.gui.text.color.EmpyreanColor
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.properties.EmpyreanItemProperties
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.player.Stats
import net.empyrean.player.StatsFormatter
import net.empyrean.util.item.ItemCachedData
import net.empyrean.util.text.Text
import net.empyrean.util.text.mutable
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

interface EmpyreanItem {
    val itemRarity: ItemRarity
    val itemKind: ItemKind
    val stats: Stats get() = Stats.empty()
    val empyreanProperties: EmpyreanItemProperties

    fun data(stack: ItemStack): ItemData?
    fun tooltip(stack: ItemStack, level: Level?, list: MutableList<Component>, isAdvanced: TooltipFlag) {
        // no additional tooltip by default
    }

    fun calculateStats(item: ItemStack): Stats {
        val pfx = (item as EmpyreanItemStack).itemData?.prefix ?: return stats
        val pfxStats = pfx.baseStats * ((1f + pfx.rarityMultiplier) * itemRarity.pfxModifier)
        return stats.merge(pfxStats)
    }

    companion object {
        @JvmStatic
        fun appendHoverTextVanilla(
            itemKind: ItemKind,
            itemRarity: ItemRarity,
            stats: Stats,
            stack: ItemStack,
            level: Level?,
            tooltipComponents: MutableList<Component>,
        ) {
            val itemData = (stack as EmpyreanItemStack).itemData
            val pfxStats = if(itemData != null) {
                val pfx = itemData.prefix
                val pfxStats = if(pfx == null) Stats.empty() else pfx.baseStats * itemRarity.pfxModifier
                pfxStats
            } else {
                Stats.empty()
            }

            if(!stats.isEmpty()) {
                // stats first!!
                if (level != null && level.isClientSide) {
                    // client injects here
                    EmpyreanTooltipEvent.CLIENT_ADD_STATS_VANILLA.invoker().addStats(stats, stack, tooltipComponents)
                } else {
                    // normal stat display
                    appendStats(stats, tooltipComponents)
                }
            }

            // then prefix stats
            if(!pfxStats.isEmpty()) {
                val pfx = itemData!!.prefix!!
                tooltipComponents.add(Component.literal("□ ").append(Component.translatable(pfx.translationKey)).append(Component.literal(" bonuses:")).withStyle(Style.EMPTY.withColor(0x7cf966)))
                val pfxValues = pfxStats.inner.values.toList()
                tooltipComponents.addAll(
                    StatsFormatter.formatExplicit(pfxStats.inner)
                        .mapIndexed { idx, it -> Component.literal(" - ").withStyle(Style.EMPTY.withColor(if(pfxValues[idx] > 0f) 0x68f74f else 0xf94868)).append(it.mutable.withStyle(Style.EMPTY.withColor(if(pfxValues[idx] > 0f) 0x8afc76 else 0xef395a))) })
            }

            // then tags
            stack.tags.forEach { tag ->
                if(tag.location.namespace == "empyrean") {
                    tooltipComponents.add(Text.translate("tag.empyrean.${tag.location.path}").withStyle(ChatFormatting.DARK_GRAY))
                }
            }

            // finally rarity
            val color = itemRarity.color as EmpyreanColor
            tooltipComponents.add(
                Component.literal("${itemRarity.named} ${itemKind.named}").withStyle(Style.EMPTY.withBold(true))
                    .withColor(color)
            )
        }

        fun appendHoverText(
            self: EmpyreanItem,
            stack: ItemStack,
            level: Level?,
            tooltipComponents: MutableList<Component>,
            isAdvanced: TooltipFlag
        ) {
            // Calculating stats
            val itemData = (stack as EmpyreanItemStack).itemData
            val pfxStats = if(itemData != null) {
                val pfx = itemData.prefix
                val pfxStats = if(pfx == null) Stats.empty() else pfx.baseStats * self.itemRarity.pfxModifier
                pfxStats
            } else {
                Stats.empty()
            }
            val stats = self.stats.merge(pfxStats)

            if(!stats.isEmpty()) {
                // stats first!!
                if (level != null && level.isClientSide) {
                    // client injects here
                    EmpyreanTooltipEvent.CLIENT_ADD_STATS.invoker().addStats(stats, self, stack, tooltipComponents)
                } else {
                    // normal stat display
                    appendStats(stats, tooltipComponents)
                }
            }

            // then item tooltip
            val previousLength = tooltipComponents.size
            self.tooltip(stack, level, tooltipComponents, isAdvanced)
            if(previousLength != tooltipComponents.size)
                tooltipComponents.add(Component.empty())

            // then prefix stats
            if(!pfxStats.isEmpty()) {
                val pfx = itemData!!.prefix!!
                tooltipComponents.add(Component.literal("□ ").append(Component.translatable(pfx.translationKey)).append(Component.literal(" bonuses:")).withStyle(Style.EMPTY.withColor(0x7cf966)))
                val pfxValues = pfxStats.inner.values.toList()
                tooltipComponents.addAll(
                    StatsFormatter.formatExplicit(pfxStats.inner)
                        .mapIndexed { idx, it -> Component.literal(" - ").withStyle(Style.EMPTY.withColor(if(pfxValues[idx] > 0f) 0x68f74f else 0xf94868)).append(it.mutable.withStyle(Style.EMPTY.withColor(if(pfxValues[idx] > 0f) 0x8afc76 else 0xef395a))) })
            }

            // then tags
            stack.tags.forEach { tag ->
                if(tag.location.namespace == "empyrean") {
                    tooltipComponents.add(Text.translate("tag.empyrean.${tag.location.path}").withStyle(ChatFormatting.DARK_GRAY))
                }
            }

            // finally rarity
            val color = self.itemRarity.color as EmpyreanColor
            tooltipComponents.add(
                Component.literal("${self.itemRarity.named} ${self.itemKind.named}").withStyle(Style.EMPTY.withBold(true))
                    .withColor(color)
            )
        }

        fun appendStats(
            stats: Stats,
            list: MutableList<Component>
        ) {
            list.addAll(StatsFormatter.format(stats))
            list.add(Component.empty())
        }

        fun appendComparisonText(
            comparedTo: ItemStack,
            selfStats: Stats,
            list: MutableList<Component>
        ) {
            if(comparedTo.item !is EmpyreanItem)
                return appendStats(selfStats, list)
            val comparedStats = (comparedTo.item as EmpyreanItem).calculateStats(comparedTo)

            list.addAll(StatsFormatter.formatDiff(selfStats, comparedStats))
            list.add(Component.empty())
        }

        fun getName(self: EmpyreanItem, stack: ItemStack): Component {
            val prefix = (stack as EmpyreanItemStack).itemData?.prefix
            return (if(prefix != null) Component.translatable(prefix.translationKey).append(Component.literal(" ")) else Component.empty()).append(Component.translatable(stack.item.descriptionId)).withColor(self.itemRarity.color as EmpyreanColor)
        }

        @JvmStatic
        fun getNameVanilla(data: ItemCachedData, stack: ItemStack): Component {
            val prefix = (stack as EmpyreanItemStack).itemData?.prefix
            return (if(prefix != null) Component.translatable(prefix.translationKey).append(Component.literal(" ")) else Component.empty()).append(Component.translatable(stack.item.descriptionId)).withColor(data.rarity.color as EmpyreanColor)
        }
    }
}