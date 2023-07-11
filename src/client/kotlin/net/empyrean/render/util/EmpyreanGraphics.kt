package net.empyrean.render.util

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.VertexConsumer
import net.empyrean.client.text.color.EmpyreanColor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.TextColor
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FastColor
import org.joml.Matrix4f

val TextColor.empyrean: EmpyreanColor get() = this as EmpyreanColor

fun GuiGraphics.drawBorderedText(
    text: String,
    x: Int,
    y: Int,
    innerColor: EmpyreanColor,
    borderColor: EmpyreanColor,
    borderWidth: Int = 1,
    centered: Boolean = false
) {
    val font = Minecraft.getInstance().font
    val inner = innerColor.colorValue
    val border = borderColor.colorValue
    val actualX = if(centered) x - (font.width(text) / 2) else x
    drawString(font, text, actualX + borderWidth, y, border, false)
    drawString(font, text, actualX - borderWidth, y, border, false)
    drawString(font, text, actualX, y + borderWidth, border, false)
    drawString(font, text, actualX, y - borderWidth, border, false)
    drawString(font, text, actualX, y, inner, false)
}

fun GuiGraphics.drawTexture(
    texture: ResourceLocation,
    x: Int,
    y: Int,
    u: Float,
    v: Float,
    width: Int,
    height: Int,
    textureWidth: Int,
    textureHeight: Int,
    z: Int = 0,
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f,
    alpha: Float = 0f
) {
    RenderSystem.setShaderColor(r, g, b, alpha)
    this.innerBlit(
        texture,
        x,
        x + width,
        y,
        y + height,
        z,
        (u + 0.0f) / textureWidth.toFloat(),
        (u + width.toFloat()) / textureWidth.toFloat(),
        (v + 0.0f) / textureHeight.toFloat(),
        (v + height.toFloat()) / textureHeight.toFloat(),
    )
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
}

fun GuiGraphics.fillBiGradient(
    fromX: Int,
    fromY: Int,
    toX: Int,
    toY: Int,
    colorFrom: Int,
    colorTo: Int,
    z: Int = 0
) {
    // Rendering first half
    this.fillGradient(
        fromX, fromY,
        toX / 2, toY, z,
        colorFrom, colorTo
    )
    // Rendering second half
    this.fillGradient(
        toX / 2, fromY,
        toX, toY, z,
        colorTo, colorFrom,
    )
}

object EmpyreanGraphics {
    fun drawGradient(
        vertex: VertexConsumer, matrix: Matrix4f,
        fromX: Int, fromY: Int,
        toX: Int, toY: Int, z: Int,
        fromColor: Int, toColor: Int
    ) {
        val a1 = FastColor.ARGB32.alpha(fromColor).toFloat() / 255.0f
        val r1 = FastColor.ARGB32.red(fromColor).toFloat() / 255.0f
        val g1 = FastColor.ARGB32.green(fromColor).toFloat() / 255.0f
        val b1 = FastColor.ARGB32.blue(fromColor).toFloat() / 255.0f
        val a2 = FastColor.ARGB32.alpha(toColor).toFloat() / 255.0f
        val r2 = FastColor.ARGB32.red(toColor).toFloat() / 255.0f
        val g2 = FastColor.ARGB32.green(toColor).toFloat() / 255.0f
        val b2 = FastColor.ARGB32.blue(toColor).toFloat() / 255.0f
        vertex.vertex(matrix, fromX.toFloat(), fromY.toFloat(), z.toFloat()).color(r1, g1, b1, a1).endVertex()
        vertex.vertex(matrix, fromX.toFloat(), toY.toFloat(), z.toFloat()).color(r2, g2, b2, a2).endVertex()
        vertex.vertex(matrix, toX.toFloat(), toY.toFloat(), z.toFloat()).color(r2, g2, b2, a2).endVertex()
        vertex.vertex(matrix, toX.toFloat(), fromY.toFloat(), z.toFloat()).color(r1, g1, b1, a1).endVertex()
    }

    fun drawBiGradient(
        vertex: VertexConsumer, matrix: Matrix4f,
        fromX: Int, fromY: Int,
        toX: Int, toY: Int, z: Int,
        fromColor: Int, toColor: Int
    ) {
        drawGradient(
            vertex, matrix,
            fromX, fromY,
            toX / 2, toY, z,
            fromColor, toColor
        )
        drawGradient(
            vertex, matrix,
            toX / 2, fromY,
            toX, toY, z,
            fromColor, toColor
        )
    }
}