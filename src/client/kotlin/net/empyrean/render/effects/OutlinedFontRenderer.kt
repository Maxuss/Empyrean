package net.empyrean.render.effects

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.util.FormattedCharSequence
import net.minecraft.util.FormattedCharSink

class OutlinedFontRenderer(
    private val outlineColor: TextColor,
    private val innerColor: TextColor = TextColor.fromRgb(0x14012b)
) : EmpyreanEffectRenderer {
    override fun renderChar(data: EmpyreanEffectRenderer.EffectRenderPayload) {

        drawCharOutlined(
            Component.literal(data.charCode.toChar().toString()).visualOrderText,
            data, if (data.style.isBold) 1f else 0.7f,
            data.alpha
        )
        if (data.style.isBold)
            drawCharOutlined(
                Component.literal(data.charCode.toChar().toString()).visualOrderText,
                data.copy(x = data.x + data.glyphInfo.boldOffset * .8f),
                1.1f,
                data.alpha
            )
    }

    @Suppress("SameParameterValue")
    private fun drawCharOutlined(
        text: FormattedCharSequence,
        data: EmpyreanEffectRenderer.EffectRenderPayload,
        outlineWidthMul: Float,
        alpha: Float
    ) {
        val i = Font.adjustColor(outlineColor.value)

        // Drawing shadow first
        val shadowSRO = Minecraft.getInstance().font.StringRenderOutput(
            data.bufferSource,
            data.x + 0.25f,
            data.y + 0.25f,
            i,
            true,
            data.pose,
            Font.DisplayMode.SEE_THROUGH,
            0xA0A0A0
        )
        shadowSRO.a = alpha * 0.5f
        text.accept { l: Int, style: Style, code: Int ->
            shadowSRO.accept(l, style.withColor(i), code)
        }
        shadowSRO.finish(0, data.x + 0.25f)

        // Drawing the outline
        val stringRenderOutput = Minecraft.getInstance().font.StringRenderOutput(
            data.bufferSource,
            0.0f,
            0.0f,
            i,
            false,
            data.pose,
            Font.DisplayMode.NORMAL,
            0xFFFFFF
        )
        stringRenderOutput.a = alpha
        for (j in -1..1) {
            for (k in -1..1) {
                if (j == 0 && k == 0) continue
                var xTmp = data.x
                text.accept { l: Int, style: Style, code: Int ->
                    val bl = style.isBold
                    stringRenderOutput.x = xTmp + j.toFloat() * data.glyphInfo.shadowOffset * outlineWidthMul
                    stringRenderOutput.y = data.y + k.toFloat() * data.glyphInfo.shadowOffset * outlineWidthMul
                    xTmp += data.glyphInfo.getAdvance(bl) * 2f
                    stringRenderOutput.accept(l, style.withColor(i), code)
                }
            }
        }

        // And the inner part
        val stringRenderOutput2 = Minecraft.getInstance().font.StringRenderOutput(
            data.bufferSource,
            data.x,
            data.y,
            Font.adjustColor(innerColor.value),
            false,
            data.pose,
            Font.DisplayMode.POLYGON_OFFSET,
            0xFFFFFF
        )
        stringRenderOutput2.a = alpha
        text.accept(stringRenderOutput2 as FormattedCharSink)
        stringRenderOutput2.finish(0, data.x)
    }
}