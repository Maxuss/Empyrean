package net.empyrean.gui.text

import com.mojang.math.Axis
import net.minecraft.Util
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.SplashRenderer
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth

class ComponentSplashRenderer(val message: Component): SplashRenderer("Uh oh!") {
    override fun render(guiGraphics: GuiGraphics, x: Int, font: Font, y: Int) {
        guiGraphics.pose().pushPose()
        guiGraphics.pose().translate(x.toFloat() / 2.0f + 123.0f, 69.0f, 0.0f)
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0f))
        var f = 1.8f - Mth.abs(Mth.sin((Util.getMillis() % 1000L).toFloat() / 1000.0f * 6.2831855f) * 0.1f)
        f = f * 100.0f / (font.width(message.string) + 32).toFloat()
        guiGraphics.pose().scale(f, f, f)
        guiGraphics.drawCenteredString(font, message, 0, -8, 0xFFFFFF) // white is the base color
        guiGraphics.pose().popPose()
    }
}