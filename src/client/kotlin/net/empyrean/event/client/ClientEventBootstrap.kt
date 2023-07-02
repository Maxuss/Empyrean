package net.empyrean.event.client

import net.empyrean.EmpyreanModClient
import net.empyrean.chat.EmpyreanStyle
import net.empyrean.chat.SpecialFormatting
import net.empyrean.components.data
import net.empyrean.debug.Debug
import net.empyrean.debug.DebugTextBatch
import net.empyrean.debug.StatDebugElement
import net.empyrean.events.EmpyreanTooltipEvent
import net.empyrean.gui.text.StatusMessageRenderer
import net.empyrean.gui.text.color.EmpyreanColors
import net.empyrean.item.EmpyreanItem.Companion.appendComparisonText
import net.empyrean.item.EmpyreanItem.Companion.appendStats
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.serverbound.ServerboundLeftClickPacket
import net.empyrean.player.Stats
import net.empyrean.render.RenderManager
import net.empyrean.render.particle.CrystalSparkleParticle
import net.empyrean.render.particle.OutlinedParticle
import net.empyrean.render.particle.ParticleEngine2D
import net.empyrean.util.general.Ticking
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

fun bootstrapClientEvents() {
    ClientPreAttackCallback.EVENT.register { _, _, clickCount ->
        if (clickCount != 0) // ensure client just pressed the button
            EmpyreanNetworking.EMPYREAN_CHANNEL.clientHandle().send(ServerboundLeftClickPacket())
        false
    }
    ComponentRenderEvent.POST.register { style, _, rd ->
        val empyrean = style as EmpyreanStyle
        if (empyrean.specialFormat == SpecialFormatting.NONE)
            return@register
        if (empyrean.specialFormat == SpecialFormatting.EMPYREAN_L_STARLIKE) {
            if (EmpyreanModClient.clientRandom.nextFloat() <= 0.002f) {
                ParticleEngine2D.spawn(CrystalSparkleParticle(rd.x - (rd.xOffset * 2f), rd.y))
            }
        } else if(empyrean.specialFormat == SpecialFormatting.EMPYREAN_L_CINDER) {
            if(EmpyreanModClient.clientRandom.nextFloat() <= 0.001f) {
                ParticleEngine2D.spawn(OutlinedParticle(rd.x - (rd.xOffset * 2f), rd.y - 10))
            }
        }
    }
    RenderTickEvent.START.register { _ ->
        EmpyreanColors.colors.forEach { (_, color) -> if (color is Ticking) color.tick() }
    }
    ClientTickEvents.END_CLIENT_TICK.register {
        StatusMessageRenderer.tick()
    }
    EmpyreanTooltipEvent.CLIENT_ADD_STATS.register { selfStats, _, _, list ->
        handleAddStats(selfStats, list)
    }
    EmpyreanTooltipEvent.CLIENT_ADD_STATS_VANILLA.register { selfStats, _, list ->
        handleAddStats(selfStats, list)
    }
    if(Debug.DEBUG_STAT_VALUES) {
        HudRenderCallback.EVENT.register { graphics, _ ->
            val playerData = Minecraft.getInstance().player?.data ?: return@register
            Minecraft.getInstance().profiler.push("debug/stats")
            val batch = DebugTextBatch(playerData.statistics!!.inner.map {
                StatDebugElement(it.key, it.value)
            }.toTypedArray())
            batch.render(graphics)
            Minecraft.getInstance().profiler.pop()
        }
    }
    ClientTickEvents.END_CLIENT_TICK.register(RenderManager)
    HudRenderCallback.EVENT.register(RenderManager)
}

private fun handleAddStats(selfStats: Stats, list: MutableList<Component>) {
    val player = Minecraft.getInstance().player
    if(!Screen.hasShiftDown() || player?.mainHandItem?.isEmpty == true)
        appendStats(selfStats, list)
    else {
        val selectedItem = player!!.mainHandItem
        if (selectedItem.isEmpty) return
        appendComparisonText(selectedItem, selfStats, list)
    }
}