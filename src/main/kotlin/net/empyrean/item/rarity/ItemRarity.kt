package net.empyrean.item.rarity

import net.empyrean.gui.text.color.EmpyreanColors
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextColor

enum class ItemRarity(
    val color: TextColor
) {
    COMMON(TextColor.fromLegacyFormat(ChatFormatting.WHITE)!!),
    RARE(TextColor.fromLegacyFormat(ChatFormatting.BLUE)!!),
    EPIC(TextColor.fromRgb(0xE537AE)),
    LEGENDARY(TextColor.fromLegacyFormat(ChatFormatting.GOLD)!!),
    RELIC(TextColor.fromRgb(0x77E537)),

    STARLIKE(EmpyreanColors.STARLIKE),
    EXPERT(EmpyreanColors.EXPERT),
    SMOLDERING(EmpyreanColors.CINDER)

    ;

    val named = name.replace("_", " ")
}