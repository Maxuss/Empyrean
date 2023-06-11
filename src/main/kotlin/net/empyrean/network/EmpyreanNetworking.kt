package net.empyrean.network

import io.wispforest.owo.network.OwoNetChannel
import net.empyrean.network.packets.PacketEmpyreanJoin
import net.minecraft.resources.ResourceLocation
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

object EmpyreanNetworking {
    val EMPYREAN_CHANNEL: OwoNetChannel = OwoNetChannel.create(ResourceLocation("empyrean:net"))

    val unauthorizedPlayers: Queue<UUID> = ConcurrentLinkedQueue()
}

fun bootstrapNetworking() {
    // Join handler
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerClientboundDeferred(PacketEmpyreanJoin::class.java)
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerServerbound(PacketEmpyreanJoin::class.java) { _, access ->
        EmpyreanNetworking.unauthorizedPlayers.remove(access.player.uuid)
    }
}