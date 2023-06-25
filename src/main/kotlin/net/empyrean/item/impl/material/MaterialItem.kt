package net.empyrean.item.impl.material

import net.empyrean.item.AbstractEmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.properties.EmpyreanItemProperties
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.util.text.Text
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class MaterialItem(properties: FabricItemSettings, rarity: ItemRarity, empyreanProperties: EmpyreanItemProperties = EmpyreanItemProperties()): AbstractEmpyreanItem(properties, rarity, ItemKind.MATERIAL, empyreanProperties) {
    constructor(properties: FabricItemSettings, rarity: ItemRarity): this(properties, rarity, EmpyreanItemProperties())

    override fun data(stack: ItemStack): ItemData? {
        return null
    }

    override fun tooltip(stack: ItemStack, level: Level?, list: MutableList<Component>, isAdvanced: TooltipFlag) {
        list.add(Text.translate("tag.empyrean.material").withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)))
    }
}