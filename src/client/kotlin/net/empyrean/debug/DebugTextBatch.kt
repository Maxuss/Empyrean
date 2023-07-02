package net.empyrean.debug

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

class DebugTextBatch(private val elements: Array<DebugElement>) {
    fun render(graphics: GuiGraphics) {
        val font = Minecraft.getInstance().font
        graphics.pose().pushPose()

        var y = 5
        for(element in elements) {
            val color = element.color.value
            val string = element.asString()

            graphics.drawString(font, string, 10, y, color)
            y += font.lineHeight
        }

        graphics.pose().popPose()
    }
}