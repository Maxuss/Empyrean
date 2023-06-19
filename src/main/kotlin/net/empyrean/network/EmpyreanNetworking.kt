package net.empyrean.network

import io.wispforest.owo.network.OwoNetChannel
import net.empyrean.item.EmpyreanItem
import net.empyrean.network.packets.PacketEmpyreanJoin
import net.empyrean.network.packets.clientbound.ClientboundStatusMessagePacket
import net.empyrean.network.packets.serverbound.ServerboundLeftClickPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
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

    // Left click handler
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerServerbound(ServerboundLeftClickPacket::class.java) { _, access ->
        val stack = access.player.mainHandItem
        if (stack == ItemStack.EMPTY || stack.item !is EmpyreanItem)
            return@registerServerbound
        val empyreanItem = stack.item as EmpyreanItem
        empyreanItem.leftClick(access.player.level(), access.player)
    }

    // Status message handler
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerClientboundDeferred(ClientboundStatusMessagePacket::class.java)
}