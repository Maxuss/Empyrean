package net.empyrean.client.text.color

import com.google.common.collect.ImmutableList
import net.empyrean.util.general.Ticking
import net.empyrean.util.text.Text
import net.minecraft.network.chat.TextColor
import net.minecraft.util.Mth

open class LerpingColor(
    val lerpName: String,
    private val colors: ImmutableList<Int>,
    private val duration: Float = 20f
) : TextColor(colors[0]), EmpyreanColor, Ticking {
    private var tick: Float = 0f
    private var idx: Int = 0

    override fun tick() {
        tick += 1f
        if (tick >= duration) {
            idx = (idx + 1) % colors.size
            tick = 0f
        }
    }

    override fun getValue(): Int {
        return colorValue
    }

    override val colorValue: Int
        get() {
            val next = colors[(idx + 1) % colors.size]
            val current = colors[idx]

            return mergeColors(current, next)
        }

    override val altValue: Int
        get() {
            val next = colors[(idx + 2) % colors.size]
            val current = colors[(idx + 1) % colors.size]

            return mergeColors(current, next)
        }

    private fun mergeColors(c1: Int, c2: Int): Int {
        val alpha = Mth.lerp(tick / duration, (c1 shr 24 and 0xFF).toFloat(), (c2 shr 24 and 0xFF).toFloat())
        val red = Mth.lerp(tick / duration, (c1 shr 16 and 0xFF).toFloat(), (c2 shr 16 and 0xFF).toFloat())
        val green = Mth.lerp(tick / duration, (c1 shr 8 and 0xFF).toFloat(), (c2 shr 8 and 0xFF).toFloat())
        val blue = Mth.lerp(tick / duration, (c1 shr 0 and 0xFF).toFloat(), (c2 shr 0 and 0xFF).toFloat())

        return Text.combineARGB(alpha, red, green, blue)

    }

    override fun serialize(): String {
        return "<E:L:$lerpName" // <E -> empyrean color prefix; L - lerping color; name -> registry index
    }

    companion object {
        @JvmStatic
        fun parse(from: String): LerpingColor {
            return EmpyreanColors.findColor(from) as LerpingColor
        }
    }
}