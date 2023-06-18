package net.empyrean.render.effects

import com.mojang.blaze3d.font.GlyphInfo
import net.minecraft.client.gui.font.FontSet
import net.minecraft.client.gui.font.glyphs.BakedGlyph
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import org.joml.Matrix4f

interface EmpyreanEffectRenderer {
    fun renderChar(
        data: EffectRenderPayload
    )

    data class EffectRenderPayload(
        val x: Float,
        val y: Float,
        val fontSet: FontSet,
        val style: Style,
        val charCode: Int,
        val glyphInfo: GlyphInfo,
        val bakedGlyph: BakedGlyph,
        val baseColor: TextColor,
        val pose: Matrix4f,
        val bufferSource: MultiBufferSource,
        val alpha: Float
    )
}