package net.empyrean.network.client

import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.PacketEmpyreanJoin
import net.fabricmc.loader.api.FabricLoader

fun bootstrapClientNetworking() {
    EmpyreanNetworking.EMPYREAN_CHANNEL.registerClientbound(PacketEmpyreanJoin::class.java) { _, _ ->
        val modVersion = FabricLoader.getInstance().getModContainer("empyrean").get().metadata.version.friendlyString
        EmpyreanNetworking.EMPYREAN_CHANNEL.clientHandle().send(PacketEmpyreanJoin(modVersion))
    }
}