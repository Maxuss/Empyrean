package net.empyrean.events

import net.empyrean.events.EmpyreanTooltipEvent.ClientAddStats
import net.empyrean.item.EmpyreanItem
import net.empyrean.player.Stats
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

object EmpyreanTooltipEvent {
    val CLIENT_ADD_STATS: Event<ClientAddStats> = EventFactory.createArrayBacked(
        ClientAddStats::class.java
    ) { callbacks ->
        ClientAddStats { selfStats, self, stack, list ->
            for (callback in callbacks) {
                callback.addStats(selfStats, self, stack, list)
            }
        }
    }

    fun interface ClientAddStats {
        fun addStats(
            selfStats: Stats,
            self: EmpyreanItem,
            stack: ItemStack,
            list: MutableList<Component>,
        )
    }
}