package net.empyrean.render.particle

import net.empyrean.render.util.drawTexture
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import kotlin.math.roundToInt

abstract class Particle2D(
    protected val lifetime: Int,
    var xo: Float,
    var yo: Float,
    val sprite: ResourceLocation,
    val spriteWidth: Int,
    val spriteHeight: Int,
    val z: Int = 0
) {
    open val gravity: Float = 2f

    @Volatile
    var isAlive: Boolean = true
        protected set

    protected var age: Int = 0

    var x: Float = xo
    var y: Float = yo

    open val r: Float = 1f
    open val g: Float = 1f
    open val b: Float = 1f
    open val alpha: Float = 1f

    open fun tick() {
        xo = x
        yo = y
        if (this.age++ >= this.lifetime) {
            isAlive = false
            return
        }
    }

    open fun render(graphics: GuiGraphics, partialTick: Float) {
        val x = Mth.lerp(partialTick, xo, x)
        val y = Mth.lerp(partialTick, yo, y)

        graphics.drawTexture(sprite, x.roundToInt(), y.roundToInt(), 0f, 0f, spriteWidth, spriteHeight, spriteWidth, spriteHeight, z, r, g, b, alpha)
    }

    companion object {
        val MAXIMUM_COLLISION_VELOCITY = Mth.square(100f)
    }
}