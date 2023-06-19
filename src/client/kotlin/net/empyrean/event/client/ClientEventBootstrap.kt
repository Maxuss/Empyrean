package net.empyrean.event.client

import net.empyrean.EmpyreanModClient
import net.empyrean.chat.EmpyreanStyle
import net.empyrean.chat.SpecialFormatting
import net.empyrean.event.client.event.client.ComponentRenderEvent
import net.empyrean.event.client.event.client.RenderTickEvent
import net.empyrean.gui.text.StatusMessageRenderer
import net.empyrean.gui.text.color.EmpyreanColors
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.serverbound.ServerboundLeftClickPacket
import net.empyrean.render.particle.CrystalSparkleParticle
import net.empyrean.render.particle.ParticleEngine2D
import net.empyrean.util.general.Ticking
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback

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
        }
    }
    RenderTickEvent.START.register { _ ->
        EmpyreanColors.colors.forEach { (_, color) -> if (color is Ticking) color.tick() }
    }
    ClientTickEvents.END_CLIENT_TICK.register {
        StatusMessageRenderer.tick()
    }
}