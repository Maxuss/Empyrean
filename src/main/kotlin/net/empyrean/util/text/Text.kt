package net.empyrean.util.text

import net.minecraft.network.chat.Component
import net.minecraft.util.Mth

object Text {
    // TODO: replace with minimessage
    fun of(msg: String) = Component.literal(msg)

    fun combineARGB(a: Number, r: Number, g: Number, b: Number): Int {
        val alpha = Mth.clamp(a.toInt(), 0, 255)
        val red = Mth.clamp(r.toInt(), 0, 255)
        val green = Mth.clamp(g.toInt(), 0, 255)
        val blue = Mth.clamp(b.toInt(), 0, 255)
        return (alpha shl 24) or (red shl 16) or (green shl 8) or (blue shl 0)
    }
}