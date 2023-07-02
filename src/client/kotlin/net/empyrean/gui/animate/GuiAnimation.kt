package net.empyrean.gui.animate

import net.minecraft.client.gui.GuiGraphics

abstract class GuiAnimation(val lifetime: Float) {
    var age: Float = 0f

    abstract fun tick()
    abstract fun render(graphics: GuiGraphics, partialTick: Float)

    open fun next(): GuiAnimation? = null

    fun tickImmediate() {
        if(age >= lifetime)
            return
        age += 1f
        tick()
    }

    inline fun tickThen(ifDone: () -> Unit) {
        if(age >= lifetime)
            ifDone()
        else {
            age += 1f
            tick()
        }
    }

    inline fun renderThen(graphics: GuiGraphics, partialTick: Float, ifDone: () -> Unit) {
        if(age >= lifetime)
            ifDone()
        else {
            render(graphics, partialTick)
        }
    }
}