package net.empyrean.event.client

import net.empyrean.event.client.ComponentRenderEvent.Callback
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.Font.DisplayMode
import net.minecraft.client.gui.font.FontSet
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.network.chat.Style
import org.joml.Matrix4f

object ComponentRenderEvent {
    @JvmField
    val PRE: Event<Callback> = EventFactory.createArrayBacked(
        Callback::class.java
    ) { callbacks ->
        Callback { style, charCode, rd ->
            for (callback in callbacks) {
                callback.accept(style, charCode, rd)
            }
        }
    }

    @JvmField
    val POST: Event<Callback> = EventFactory.createArrayBacked(
        Callback::class.java
    ) { callbacks ->
        Callback { style, charCode, rd ->
            for (callback in callbacks) {
                callback.accept(style, charCode, rd)
            }
        }
    }

    fun interface Callback {
        fun accept(style: Style, charCode: Int, rd: RenderData)
    }

    data class RenderData(
        val xOffset: Float,
        val x: Float,
        val y: Float,
        val dropShadow: Boolean,
        val matrix: Matrix4f,
        val bufferSource: MultiBufferSource,
        val displayMode: DisplayMode,
        val font: Font,
        val fontSet: FontSet
    )
}