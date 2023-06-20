package net.empyrean.item

import net.empyrean.chat.withColor
import net.empyrean.gui.text.color.EmpyreanColor
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

interface EmpyreanItem {
    val itemRarity: ItemRarity
    val itemKind: ItemKind

    fun data(stack: ItemStack): ItemData
    fun tooltip(stack: ItemStack, level: Level?, list: MutableList<Component>, isAdvanced: TooltipFlag) {
        // no additional tooltip by default
    }

    companion object {
        fun appendHoverText(
            self: EmpyreanItem,
            stack: ItemStack,
            level: Level?,
            tooltipComponents: MutableList<Component>,
            isAdvanced: TooltipFlag
        ) {
            self.tooltip(stack, level, tooltipComponents, isAdvanced)
            tooltipComponents.add(Component.empty())
            val color = self.itemRarity.color as EmpyreanColor
            tooltipComponents.add(
                Component.literal("${self.itemRarity.named} ${self.itemKind.named}").withStyle(Style.EMPTY.withBold(true))
                    .withColor(color)
            )
        }

        fun getName(self: EmpyreanItem, stack: ItemStack): Component {
            return Component.translatable(stack.item.descriptionId).withColor(self.itemRarity.color as EmpyreanColor)
        }
    }
}