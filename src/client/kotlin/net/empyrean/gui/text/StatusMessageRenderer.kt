package net.empyrean.gui.text

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence
import net.minecraft.util.Mth
import java.util.*
import kotlin.jvm.optionals.getOrNull
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object StatusMessageRenderer {
    private var currentMessage: Component? = null
    private var ticksUntilShift: Int = -1
    private var tickDelay: Int = -1
    private var backdropOpacity: Float = 0f
    private var backdropOpacityO: Float = 0f
    private var messageOpacity: Float = 1f
    private var messageOpacityO: Float = 1f
    private var isMessageAlive: Boolean = false
    private var offset: Int = -1

    fun status(message: Component, delayFrames: Int) {
        this.currentMessage = message
        this.ticksUntilShift = delayFrames
        this.tickDelay = delayFrames
        messageOpacity = 1f
        isMessageAlive = true
        offset = 0
    }

    @JvmStatic
    fun render(graphics: GuiGraphics, partialTick: Float) {
        if(Minecraft.getInstance().isPaused || currentMessage == null)
            return
        Minecraft.getInstance().profiler.push("empyrean:statusMessage")

        var msg = processMsg()
        if(msg == null) {
            isMessageAlive = false
            msg = currentMessage!!.visualOrderText
        }

        RenderSystem.enableBlend()
        graphics.pose().pushPose()
        graphics.pose().translate((graphics.guiWidth() / 2f), (graphics.guiHeight() / 2f), 0.0f)

        val width = Minecraft.getInstance().font.width(currentMessage!!)
        val textOpacity = (Mth.lerp(partialTick, messageOpacityO, messageOpacity) * 255f).roundToInt()
        graphics.drawString(Minecraft.getInstance().font, msg!!, -width / 2, -15, 0xFFFFFF + (textOpacity shl 24))

        RenderSystem.disableBlend()

        graphics.pose().popPose()

        Minecraft.getInstance().profiler.pop()
    }

    @JvmStatic
    fun renderBackdrop(graphics: GuiGraphics, partialTick: Float) {
        if(Minecraft.getInstance().isPaused || currentMessage == null)
            return

        graphics.pose().pushPose()

        val actualOpacity = (Mth.lerp(partialTick, backdropOpacityO, backdropOpacity) * 128).roundToInt()
        val color = 0x101010 + (actualOpacity shl 24)

        RenderSystem.enableBlend()

        graphics.fillGradient(
            0, 0,
            graphics.guiWidth(), graphics.guiHeight(),
            color, 0x101010 + ((actualOpacity * 1.5f).roundToInt() shl 24)
        )

        RenderSystem.disableBlend()

        graphics.pose().popPose()
    }

    fun tick() {
        if(!isMessageAlive && currentMessage != null) {
            if(backdropOpacity > 0f) {
                backdropOpacityO = backdropOpacity
                backdropOpacity = max(0f, backdropOpacity - 0.05f)
            }
            if(messageOpacity > 0f) {
                messageOpacityO = messageOpacity
                messageOpacity = max(0f, messageOpacity - 0.05f)
            }
            if(backdropOpacity == 0f && messageOpacity == 0f) {
                currentMessage = null
                ticksUntilShift = -1
                tickDelay = -1
                offset = 0
                backdropOpacityO = 0f
                messageOpacity = 1f
                messageOpacityO = 1f
            }
        } else if(isMessageAlive && currentMessage != null) {
            if(backdropOpacity < 1f) {
                backdropOpacityO = backdropOpacity
                backdropOpacity = min(1f, backdropOpacity + 0.1f)
            } else {
                backdropOpacityO = backdropOpacity
            }

            if(--ticksUntilShift <= 0) {
                ticksUntilShift = tickDelay
                offset++
            }
        }
    }

    private fun processMsg(): FormattedCharSequence? {
        val msg = currentMessage!!
        // recomposing sequence
        val component = msg.contents.visit({ style, txt ->
            // all components should already be resolved at this point, so we can construct a simple literal
            val length = txt.length
            if(offset >= length)
                return@visit Optional.empty()
            Optional.of(Component.literal(txt.substring(0, offset)).withStyle(style))
        }, msg.style).getOrNull()?.visualOrderText
        return component
    }

}