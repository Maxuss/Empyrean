package net.empyrean.render

import net.empyrean.chat.EmpyreanStyle
import net.empyrean.chat.SpecialFormatting
import net.minecraft.client.gui.Font
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.network.chat.Style
import net.minecraft.util.FormattedCharSink
import org.joml.Matrix4f

class EmpyreanTextPostRender(
    val x: Float,
    val y: Float,
    val drawShadow: Boolean,
    val pose: Matrix4f,
    val bufferSource: MultiBufferSource,
    val displayMode: Font.DisplayMode,
    val font: Font
) : FormattedCharSink {
    override fun accept(color: Int, style: Style, charCode: Int): Boolean {
        if ((style as EmpyreanStyle).specialFormat == SpecialFormatting.NONE)
            return true
        val fs = (font as IFontAccessor).fontSet(style.font)
        val consumer = bufferSource.getBuffer(fs.getGlyph(charCode).renderType(displayMode))
        consumer.vertex(x.toDouble(), y.toDouble(), 1.0).color(0xFFAAFF).uv(0.0f, 1.0f).uv2(color).endVertex()
        return false
    }
}