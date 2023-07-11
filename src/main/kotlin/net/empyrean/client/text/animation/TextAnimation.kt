package net.empyrean.client.text.animation

import kotlinx.serialization.Serializable
import net.minecraft.Util
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec2


private val RANDOM_SHAKE_DIRECTIONS = Util.make(arrayOfNulls<Vec2>(30)) { accum ->
    val step = (Math.PI * 2 / accum.size).toFloat()
    for (i in accum.indices) {
        val rad = step * i
        accum[i] = Vec2(Mth.cos(rad), Mth.sin(rad))
    }
    accum.shuffle()
}

@Serializable
enum class TextAnimation: EmpyreanTextAnimation {
    NONE {
        override fun apply(to: CharRenderContext) {
            return // no logic by default
        }
    },
    SHAKE {
        override fun apply(to: CharRenderContext) {
            val shift = RANDOM_SHAKE_DIRECTIONS[(Util.getMillis() * .01f + to.index).toInt() % RANDOM_SHAKE_DIRECTIONS.size]!!
            to.yOffset += shift.y * .6f
            to.xOffset += shift.x * .6f
        }
    },

    WAVE {
        override fun apply(to: CharRenderContext) {
            to.yOffset += Mth.sin(Util.getMillis() * .01f + to.index) * 2f
        }
    },

    WOBBLE {
        override fun apply(to: CharRenderContext) {
            val shift = RANDOM_SHAKE_DIRECTIONS[to.index % RANDOM_SHAKE_DIRECTIONS.size]!!
            val mod = Mth.sin(Util.getMillis() * .01f + to.index * 2f) * 1.5f
            to.yOffset += shift.y * mod
            to.xOffset += shift.x * mod
        }
    }
}