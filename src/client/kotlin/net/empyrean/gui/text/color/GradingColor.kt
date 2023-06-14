package net.empyrean.gui.text.color

import net.empyrean.event.client.event.client.RenderTickEvent
import net.empyrean.util.general.BidiTicker
import net.empyrean.util.text.Text
import net.minecraft.network.chat.TextColor
import net.minecraft.util.Mth

class GradingColor(
    private val from: Int,
    private val to: Int,
    private val duration: Float = 20f
): TextColor(from) {
    private var tick: BidiTicker = BidiTicker(0f, duration, 1f)

    init {
        RenderTickEvent.START.register(this::onRenderTick)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onRenderTick(partialTick: Float) {
        tick.tick()
    }

    override fun getValue(): Int {
        val step = tick.currentValue

        val alpha = Mth.lerp(step / duration, (from shr 24 and 0xFF).toFloat(), (to shr 24 and 0xFF).toFloat())
        val red = Mth.lerp(step / duration, (from shr 16 and 0xFF).toFloat(), (to shr 16 and 0xFF).toFloat())
        val green = Mth.lerp(step / duration, (from shr 8 and 0xFF).toFloat(), (to shr 8 and 0xFF).toFloat())
        val blue = Mth.lerp(step / duration, (from shr 0 and 0xFF).toFloat(), (to shr 0 and 0xFF).toFloat())

        val combined = Text.combineARGB(alpha, red, green, blue)
        return combined
    }
}