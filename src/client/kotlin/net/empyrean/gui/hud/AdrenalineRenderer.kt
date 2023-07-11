package net.empyrean.gui.hud

import net.empyrean.EmpyreanModClient
import net.empyrean.components.data
import net.empyrean.effect.EmpyreanEffects
import net.empyrean.client.text.color.EmpyreanColor
import net.empyrean.client.text.color.EmpyreanColors
import net.empyrean.gui.util.TexturedAnimation
import net.empyrean.render.util.drawBorderedText
import net.empyrean.render.util.drawTexture
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.TextColor
import net.minecraft.resources.ResourceLocation
import kotlin.math.roundToInt

object AdrenalineRenderer: ClientTickEvents.EndTick {
    private val ADRENALINE_BAR_LOCATION = ResourceLocation(EmpyreanModClient.MODID, "textures/gui/hud/adrenaline_bars.png")
    private val ADRENALINE_FULL_LOCATION = ResourceLocation(EmpyreanModClient.MODID, "textures/gui/hud/adrenaline_full.png")
    private const val ADRENALINE_ANIMATION_Y_OFFSET = 10
    private const val ADRENALINE_ANIMATION_X = 190
    private var shouldRender = false
    private var animation = TexturedAnimation(ADRENALINE_FULL_LOCATION, ADRENALINE_ANIMATION_X, ADRENALINE_ANIMATION_Y_OFFSET, 5, 5)

    override fun onEndTick(client: Minecraft) {
        val adrenalineLevel = client.player?.data?.adrenalineLevel ?: 0f
        val player = client.player ?: return
        if(!player.hasEffect(EmpyreanEffects.BOSS_PRESENCE) && shouldRender) {
            shouldRender = false
        } else if(adrenalineLevel > 0f && !shouldRender) {
            shouldRender = true
        } else if(adrenalineLevel < 1f && animation.hasRendered()) {
            animation.reset()
        }
    }

    @JvmStatic
    fun shouldRender(): Boolean {
        return shouldRender
    }

    private fun renderAdrenalineFull(ticks: Int, graphics: GuiGraphics, x: Int, y: Int) {
        animation.render(graphics, ticks, x - 2, y)
    }

    @JvmStatic
    fun renderAdrenaline(ticks: Int, adrenalineLevel: Float, graphics: GuiGraphics, x: Int) {
        if(!shouldRender) {
            return
        }
        val mc = Minecraft.getInstance()
        mc.profiler.push("adrenalineBar")
        val actualX = x - 3
        val y = graphics.guiHeight() - 32 + 1
        val adrenalineBarOffset = (adrenalineLevel * 184f).roundToInt()

        graphics.drawTexture(
            ADRENALINE_BAR_LOCATION,
            actualX, y,
            0f, 0f,
            188, 8,
            188, 13,
            alpha = 1f
        )
        graphics.drawTexture(
            ADRENALINE_BAR_LOCATION,
            actualX, y + 3,
            0f, 8f,
            adrenalineBarOffset, 5,
            188, 13,
            alpha = 1f
        )
        mc.profiler.pop()

        mc.profiler.push("adrenalineAmount")
        val adrenalineAmount = "${(adrenalineLevel * 100f).roundToInt()}%"
        graphics.drawBorderedText(adrenalineAmount, graphics.guiWidth() / 2, graphics.guiHeight() - 31 - 4, EmpyreanColors.ADRENALINE, TextColor.fromRgb(0) as EmpyreanColor, 1, true)
        mc.profiler.pop()

        if(adrenalineLevel >= 1f) {
            // start rendering animation earlier
            renderAdrenalineFull(ticks, graphics, actualX, y)
        }
    }

    enum class State {
        FADING_IN,
        RENDER,
        DO_NOT_RENDER
    }
}