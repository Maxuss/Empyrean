package net.empyrean.events

import net.empyrean.events.EmpyreanPlayerEvents.PlayerCalculateStats
import net.empyrean.events.EmpyreanPlayerEvents.PlayerTick
import net.empyrean.player.Stats
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

    @JvmField
    val PLAYER_CALCULATE_STATS: Event<PlayerCalculateStats> = EventFactory.createArrayBacked(
        PlayerCalculateStats::class.java
    ) { callbacks ->
        PlayerCalculateStats { stats ->
            for (callback in callbacks) {
                callback.processStats(stats)
            }
        }
    }

    fun interface PlayerTick {
        fun onPlayerTick(player: ServerPlayer)
    }
    fun interface PlayerCalculateStats {
        fun processStats(origin: Stats)
    }

}