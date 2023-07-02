package net.empyrean.gui.animate

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.util.Mth
import kotlin.math.min

class SlideInAnimation(ticks: Float, private val xDistance: Float, private val speed: Float, private val easing: Interpolation, private val slideOut: Boolean = false): GuiAnimation(ticks)  {
    var x: Float = 0f
    override fun tick() {
        // No tick logic
    }

    override fun render(graphics: GuiGraphics, partialTick: Float) {
        val lerped = Mth.lerp(easing(age / lifetime), x, x + speed)
        x = min(lerped, xDistance)

        graphics.pose().translate(x, 0f, 0f)
    }

    override fun next(): GuiAnimation? {
        return if(slideOut)
            SlideOutAnimation(lifetime, xDistance, 0f, speed, easing)
        else null
    }
}

class SlideOutAnimation(ticks: Float, private val from: Float, private val xMin: Float, private val speed: Float, private val easing: Interpolation): GuiAnimation(ticks) {
    var advance: Float = 0f
    override fun tick() {
        // No tick logic
    }

    override fun render(graphics: GuiGraphics, partialTick: Float) {
        val lerped = Mth.lerp(easing(age / lifetime), advance, advance + from)
        advance = min(from + xMin, lerped)

        graphics.pose().translate(from - advance, 0f, 0f)
    }
}

enum class Interpolation(val processor: (Float) -> Float) {
    EASE_IN({ x -> x * x }),
    EASE_OUT({ x -> FLIP(EASE_IN(FLIP(x))) }),
    EASE_IN_OUT({ x -> Mth.lerp(x, EASE_IN(x), EASE_OUT(x)) }),

    SPIKE({ x -> if(x <= .5f) EASE_IN(x / .5f) else EASE_IN(FLIP(x) / .5f) }),
    SPIKE_2({ x -> SPIKE(x * x)}),

    FLIP({ x -> 1 - x }),

    SINE({ x -> Mth.sin(x) }),
    COSINE({ x -> Mth.cos(x) })

    ;

    operator fun invoke(x: Float) = processor(x)
}