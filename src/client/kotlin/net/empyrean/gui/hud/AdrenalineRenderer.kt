package net.empyrean.gui.hud

import net.empyrean.EmpyreanModClient
import net.empyrean.components.data
import net.empyrean.effect.EmpyreanEffects
import net.empyrean.gui.text.color.EmpyreanColor
import net.empyrean.gui.text.color.EmpyreanColors
import net.empyrean.render.util.drawBorderedText
import net.empyrean.render.util.drawTexture
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.TextColor
import net.minecraft.resources.ResourceLocation
import java.text.DecimalFormat
import kotlin.math.roundToInt

object AdrenalineRenderer: ClientTickEvents.EndTick {
    private val ADRENALINE_BAR_LOCATION = ResourceLocation(EmpyreanModClient.MODID, "textures/gui/hud/adrenaline_bars.png")
    private val RENDER_FORMAT = DecimalFormat("#.##")

    private var shouldRender: Boolean = false

    override fun onEndTick(client: Minecraft) {
        val adrenalineLevel = client.player?.data?.adrenalineLevel ?: 0f
        val player = client.player ?: return
        if(!player.hasEffect(EmpyreanEffects.BOSS_PRESENCE) && shouldRender) {
            shouldRender = false
        } else if(adrenalineLevel > 0f && !shouldRender) {
            shouldRender = true
        }
    }

    @JvmStatic
    fun shouldRender(): Boolean {
        return shouldRender
    }

    @JvmStatic
    fun renderAdrenaline(adrenalineLevel: Float, graphics: GuiGraphics, x: Int) {
        if(!shouldRender) {
            return
        }
        Minecraft.getInstance().profiler.push("adrenalineBar")
        val actualX = x - 3
        val y = graphics.guiHeight() - 32 + 1
        val adrenalineBarOffset = (adrenalineLevel * 184f).roundToInt()

        val adrenalineAmount = "${(adrenalineLevel * 100f).roundToInt()}%"

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
        Minecraft.getInstance().profiler.pop()
        Minecraft.getInstance().profiler.push("adrenalineAmount")
        graphics.drawBorderedText(adrenalineAmount, graphics.guiWidth() / 2, graphics.guiHeight() - 31 - 4, EmpyreanColors.ADRENALINE, TextColor.fromRgb(0) as EmpyreanColor, 1, true)
        Minecraft.getInstance().profiler.pop()
    }

    enum class State {
        FADING_IN,
        RENDER,
        DO_NOT_RENDER
    }
}