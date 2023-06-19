package net.empyrean.render.effects

import com.mojang.blaze3d.systems.RenderSystem
import net.empyrean.EmpyreanModClient
import net.empyrean.render.util.drawTexture
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FastColor
import kotlin.math.roundToInt

object BloomRenderer {
    private val queue: MutableList<BakedBloom> = mutableListOf()

    @Volatile
    private var active: Boolean = !Minecraft.getInstance().isPaused

    @JvmStatic
    fun enqueue(effect: BakedBloom) {
        if (active)
            queue.add(effect)
    }

    @JvmStatic
    fun render(to: GuiGraphics) {
        if (queue.isEmpty() || !active)
            return
        Minecraft.getInstance().profiler.push("empyrean:textBloom")
        to.pose().pushPose()
        to.pose().translate(0f, 0f, -50f)
        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()

        for (each in queue) {
            renderSingle(each, to)
        }
        queue.clear()

        to.pose().popPose()
        Minecraft.getInstance().profiler.pop()
    }

    private fun renderSingle(effect: BakedBloom, graphics: GuiGraphics) {
        val spriteWidth: Float = effect.size * 1.1f
        val spriteHeight = 10f
        graphics.drawTexture(
            ResourceLocation(EmpyreanModClient.MODID, "textures/gui/particles/bloom.png"),
            (effect.x1 * 1f).roundToInt(), effect.y1,
            0f,
            0f,
            spriteWidth.toInt(), spriteHeight.toInt(),
            spriteWidth.toInt(), spriteHeight.toInt(),
            -5,
            FastColor.ARGB32.red(effect.color) / 255f,
            FastColor.ARGB32.green(effect.color) / 255f,
            FastColor.ARGB32.blue(effect.color) / 255f,
            0.5f
        )
    }
}

data class BakedBloom(
    val color: Int,
    val size: Int,
    val x1: Int,
    val y1: Int,
)