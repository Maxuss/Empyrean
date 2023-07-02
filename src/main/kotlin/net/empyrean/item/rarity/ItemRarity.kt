package net.empyrean.item.rarity

import kotlinx.serialization.Serializable
import net.empyrean.gui.text.color.EmpyreanColors
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextColor

@Serializable
enum class ItemRarity(
    val color: TextColor,
    val pfxModifier: Float = 1f
) {
    COMMON(TextColor.fromLegacyFormat(ChatFormatting.WHITE)!!),
    UNCOMMON(TextColor.fromRgb(0x6fdcf7)),
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