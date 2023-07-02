@file:Suppress("FunctionName")

package net.empyrean.render.tasks.premade

import com.mojang.blaze3d.systems.RenderSystem
import net.empyrean.gui.animate.Interpolation
import net.empyrean.render.tasks.renderTask
import net.minecraft.util.Mth
import kotlin.math.roundToInt

fun DarkenBackgroundTask(lifetime: Float, darkenOut: Boolean = false) = renderTask(lifetime) {
    var opacityO = 0f

    render { graphics, ticks ->
        val alpha = Mth.lerp(Interpolation.SPIKE(age / lifetime), 0f, 1f)
        val newOpacity = Mth.lerp(ticks, opacityO, alpha)
        opacityO = newOpacity
        val opacity = (newOpacity * 128f).roundToInt()
        val color = 0x101010 + (opacity shl 24)

        RenderSystem.enableBlend()

        graphics.fillGradient(
            0, 0,
            graphics.guiWidth(), graphics.guiHeight(),
            color, 0x101010 + ((opacity * 1.5f).roundToInt() shl 24)
        )

        RenderSystem.disableBlend()
    }

    next {
        if(darkenOut)
            DarkenBackgroundOutTask(lifetime)
        else
            null
    }
}

fun DarkenBackgroundOutTask(lifetime: Float) = renderTask(lifetime) {
    var opacityO = 1f

    render { graphics, ticks ->
        val alpha = Mth.lerp(Interpolation.SPIKE(age / lifetime), 1f, 0f)
        val newOpacity = Mth.lerp(ticks, opacityO, alpha)
        opacityO = newOpacity
        val opacity = (newOpacity * 128f).roundToInt()
        val color = 0x101010 + (opacity shl 24)

        RenderSystem.enableBlend()

        graphics.fillGradient(
            0, 0,
            graphics.guiWidth(), graphics.guiHeight(),
            color, 0x101010 + ((opacity * 1.5f).roundToInt() shl 24)
        )

        RenderSystem.disableBlend()
    }
}