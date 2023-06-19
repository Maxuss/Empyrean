package net.empyrean.gui.button

import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.network.chat.Component

class DisabledButton(x: Int, y: Int, width: Int, height: Int, text: Component, eTooltip: Tooltip) :
    Button(x, y, width, height, text, { }, { it.get() }) {
    init {
        active = false
        tooltip = eTooltip
    }

    override fun shouldDrawTooltip(mouseX: Double, mouseY: Double): Boolean {
        return true
    }
}