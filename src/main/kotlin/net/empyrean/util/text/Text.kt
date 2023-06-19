package net.empyrean.util.text

import net.empyrean.gui.text.color.EmpyreanColor
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.util.FastColor.ARGB32
import net.minecraft.util.Mth
import kotlin.math.roundToInt

object Text {
    // TODO: replace with minimessage
    @JvmStatic
    fun of(msg: String) = Component.literal(msg)

    @JvmStatic
    fun combineARGB(a: Number, r: Number, g: Number, b: Number): Int {
        val alpha = Mth.clamp(a.toInt(), 0, 255)
        val red = Mth.clamp(r.toInt(), 0, 255)
        val green = Mth.clamp(g.toInt(), 0, 255)
        val blue = Mth.clamp(b.toInt(), 0, 255)
        return (alpha shl 24) or (red shl 16) or (green shl 8) or (blue shl 0)
    }

    @JvmStatic
    fun mergeWithAlpha(rgb: Int, alpha: Int): Int {
        return rgb + (alpha shl 24)
    }

    @JvmStatic
    fun extractARGB(from: Int): ARGBColor {
        val alpha = ARGB32.alpha(from)
        val red = ARGB32.red(from)
        val green = ARGB32.green(from)
        val blue = ARGB32.blue(from)
        return ARGBColor(alpha, red, green, blue)
    }

    @JvmStatic
    fun multiplyColor(color: Int, by: Float, affectsAlpha: Boolean = false): Int {
        val (a, r, g, b) = extractARGB(color)
        val newR = (r * by).roundToInt()
        val newG = (g * by).roundToInt()
        val newB = (b * by).roundToInt()
        val newA = if (affectsAlpha) (a * by).roundToInt() else a
        return combineARGB(newA, newR, newG, newB)
    }

    operator fun EmpyreanColor.times(other: Float): Int {
        return multiplyColor(colorValue, other)
    }
}

data class ARGBColor(
    val alpha: Int,
    val red: Int,
    val green: Int,
    val blue: Int
)

val Component.mutable: MutableComponent get() = this as MutableComponent