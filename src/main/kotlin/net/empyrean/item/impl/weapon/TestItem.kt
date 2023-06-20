package net.empyrean.item.impl.weapon

import net.empyrean.item.AbstractEmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class TestItem : AbstractEmpyreanItem(FabricItemSettings().fireproof(), ItemRarity.STARLIKE, ItemKind.MATERIAL) {
    override fun data(stack: ItemStack): ItemData {
        return ItemData(stack)
    }

    override fun tooltip(itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag) {
        list.add(Component.literal("Material").withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)))
        list.add(Component.empty())
        list.add(
            Component.literal("Can be used to craft Cosmilium gear")
                .withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
        )
    }
}