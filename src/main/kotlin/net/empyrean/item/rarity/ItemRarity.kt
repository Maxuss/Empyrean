package net.empyrean.item.rarity

import net.empyrean.gui.text.color.EmpyreanColors
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextColor

enum class ItemRarity(
    val color: TextColor,
    val pfxModifier: Float = 1f
) {
    COMMON(TextColor.fromLegacyFormat(ChatFormatting.WHITE)!!),
    RARE(TextColor.fromLegacyFormat(ChatFormatting.BLUE)!!),
    EPIC(TextColor.fromRgb(0xE537AE)),
    LEGENDARY(TextColor.fromLegacyFormat(ChatFormatting.GOLD)!!, 2f),
    RELIC(TextColor.fromRgb(0x77E537), 2f),

    STARLIKE(EmpyreanColors.STARLIKE, 2.5f),
    EXPERT(EmpyreanColors.EXPERT),
    SMOLDERING(EmpyreanColors.CINDER, 2f)

    ;

    val named = name.replace("_", " ")
}