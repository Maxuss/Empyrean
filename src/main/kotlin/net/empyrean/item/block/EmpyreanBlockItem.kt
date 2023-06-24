package net.empyrean.item.block

import net.empyrean.item.EmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.properties.EmpyreanItemProperties
import net.empyrean.item.rarity.ItemRarity
import net.minecraft.network.chat.Component
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

class EmpyreanBlockItem(block: Block, override val itemRarity: ItemRarity,
                        override val empyreanProperties: EmpyreanItemProperties = EmpyreanItemProperties()
): BlockItem(block, Properties()), EmpyreanItem {
    constructor(block: Block, rarity: ItemRarity): this(block, rarity, EmpyreanItemProperties())

    override val itemKind: ItemKind = ItemKind.MATERIAL

    override fun data(stack: ItemStack): ItemData? {
        return null
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        EmpyreanItem.appendHoverText(this, stack, level, tooltipComponents, isAdvanced)
    }

    override fun getName(stack: ItemStack): Component {
        return EmpyreanItem.getName(this, stack)
    }
}