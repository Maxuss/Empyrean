package net.empyrean.util.text

import net.minecraft.network.chat.Component

object Text {
    // TODO: replace with minimessage
    fun of(msg: String) = Component.literal(msg)
}