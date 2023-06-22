package net.empyrean.item.impl.material

import net.empyrean.item.AbstractEmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.util.text.Text
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class MaterialItem(properties: FabricItemSettings, rarity: ItemRarity): AbstractEmpyreanItem(properties, rarity, ItemKind.MATERIAL) {
    override fun data(stack: ItemStack): ItemData {
        return ItemData(stack)
    }

    override fun tooltip(stack: ItemStack, level: Level?, list: MutableList<Component>, isAdvanced: TooltipFlag) {
        list.add(Text.of("Material").withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)))
    }
}