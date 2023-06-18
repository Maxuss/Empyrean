package net.empyrean.render

import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.gui.font.FontSet
import net.minecraft.client.gui.font.glyphs.BakedGlyph
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

interface IFontAccessor {
    fun fontSet(rl: ResourceLocation): FontSet

    fun renderChar(
        glyph: BakedGlyph?,
        bold: Boolean,
        italic: Boolean,
        boldOffset: Float,
        x: Float,
        y: Float,
        matrix: Matrix4f?,
        buffer: VertexConsumer?,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float,
        packedLight: Int
    )

}