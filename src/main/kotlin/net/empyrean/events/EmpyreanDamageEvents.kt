package net.empyrean.events

import net.empyrean.events.EmpyreanDamageEvents.ClientPlayerDamaged
import net.empyrean.events.EmpyreanDamageEvents.ServerPlayerDamaged
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.player.Player

// TODO: empyrean damage system
object EmpyreanDamageEvents {
    @JvmField
    val SERVER_PLAYER_DAMAGED: Event<ServerPlayerDamaged> = EventFactory.createArrayBacked(
        ServerPlayerDamaged::class.java
    ) { callbacks ->
        ServerPlayerDamaged { player, damage, source ->
            for (callback in callbacks) {
                callback.onDamage(player, damage, source)
            }
        }
    }

    @JvmField
    val CLIENT_PLAYER_DAMAGED: Event<ClientPlayerDamaged> = EventFactory.createArrayBacked(
        ClientPlayerDamaged::class.java
    ) { callbacks ->
        ClientPlayerDamaged { player, damage, source ->
            for (callback in callbacks) {
                callback.onDamageClient(player, damage, source)
            }
        }
    }

    fun interface ServerPlayerDamaged {
        fun onDamage(player: ServerPlayer, damage: Float, source: DamageSource)
    }

    fun interface ClientPlayerDamaged {
        fun onDamageClient(player: Player, damage: Float, source: DamageSource)
    }
}