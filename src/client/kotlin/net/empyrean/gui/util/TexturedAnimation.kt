package net.empyrean.gui.util

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

// very scuffed please ignore this
class TexturedAnimation(
    val location: ResourceLocation,
    val width: Int, val frameHeight: Int,
    val frameCount: Int, val frameTime: Int
) {
    private var frame: Int = 0

    fun reset() {
        frame = 0
    }

    fun hasRendered(): Boolean {
        return frame >= frameCount
    }

    fun render(to: GuiGraphics, ticks: Int, x: Int, y: Int) {
        if(hasRendered())
            return
        val currentFrame = if(ticks % (frameTime) != 0) frame else ticks % frameCount
        val baseOffset = (frameHeight * currentFrame).toFloat()
        to.blit(
            location,
            x, y,
            0f, baseOffset,
            width, frameHeight,
            width, frameHeight * frameCount
        )
        frame = currentFrame
    }
}