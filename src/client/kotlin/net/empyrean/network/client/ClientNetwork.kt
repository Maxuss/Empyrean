package net.empyrean.network.client

import net.empyrean.gui.text.StatusMessageRenderer
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.PacketEmpyreanJoin
import net.empyrean.network.packets.clientbound.ClientboundStatusMessagePacket
import net.fabricmc.loader.api.FabricLoader

fun bootstrapClientNetworking() {
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerClientbound(PacketEmpyreanJoin::class.java) { _, _ ->
        val modVersion = FabricLoader.getInstance().getModContainer("empyrean").get().metadata.version.friendlyString
        EmpyreanNetworking.EMPYREAN_CHANNEL.clientHandle().send(PacketEmpyreanJoin(modVersion))
    }
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerClientbound(ClientboundStatusMessagePacket::class.java) { packet, _ ->
        StatusMessageRenderer.status(packet.message, packet.delayFrames)
    }
}