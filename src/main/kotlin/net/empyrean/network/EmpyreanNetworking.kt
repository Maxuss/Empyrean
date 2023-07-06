package net.empyrean.network

import io.wispforest.owo.network.OwoNetChannel
import io.wispforest.owo.network.ServerAccess
import net.empyrean.feature.adrenaline.AdrenalineManager
import net.empyrean.item.AbstractEmpyreanItem
import net.empyrean.network.packets.PacketEmpyreanJoin
import net.empyrean.network.packets.clientbound.ClientboundStatusMessagePacket
import net.empyrean.network.packets.serverbound.ServerboundPlayerActionPacket
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

    // Player action handler
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerServerbound(ServerboundPlayerActionPacket::class.java) { data, access ->
        when(data.action) {
            ServerboundPlayerActionPacket.Action.LEFT_CLICK -> actionLeftClick(access)
            ServerboundPlayerActionPacket.Action.ACTIVATE_ADRENALINE -> actionActivateAdrenaline(access)
        }
    }

    // Status message handler
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerClientboundDeferred(ClientboundStatusMessagePacket::class.java)
}

private fun actionLeftClick(access: ServerAccess) {
    val stack = access.player.mainHandItem
    if (stack == ItemStack.EMPTY || stack.item !is AbstractEmpyreanItem)
        return
    val empyreanItem = stack.item as AbstractEmpyreanItem
    empyreanItem.leftClick(access.player.level(), access.player)
}

private fun actionActivateAdrenaline(access: ServerAccess) {
    AdrenalineManager.tryActivateAdrenaline(access.player)
}