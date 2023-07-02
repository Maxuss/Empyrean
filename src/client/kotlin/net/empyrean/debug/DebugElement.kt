package net.empyrean.debug

import net.empyrean.player.PlayerStat
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor

interface DebugElement {
    val color: TextColor get() = TextColor.fromLegacyFormat(ChatFormatting.WHITE)!!
    fun asString(): Component
}

data class StatDebugElement(val stat: PlayerStat, val value: Float): DebugElement {
    override fun asString(): Component {
        return Component.translatable(stat.componentKey).append(" $value")
    }

    override val color: TextColor = stat.color
}