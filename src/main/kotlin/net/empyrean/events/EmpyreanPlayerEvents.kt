package net.empyrean.events

import net.empyrean.events.EmpyreanPlayerEvents.PlayerTick
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.level.ServerPlayer

object EmpyreanPlayerEvents {
    @JvmField
    val PLAYER_TICK: Event<PlayerTick> = EventFactory.createArrayBacked(
        PlayerTick::class.java
    ) { callbacks ->
        PlayerTick { player ->
            for (callback in callbacks) {
                callback.onPlayerTick(player)
            }
        }
    }

    fun interface PlayerTick {
        fun onPlayerTick(player: ServerPlayer)
    }
}