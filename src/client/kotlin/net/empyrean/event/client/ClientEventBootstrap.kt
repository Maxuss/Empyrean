package net.empyrean.event.client

import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.serverbound.ServerboundLeftClickPacket
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback

fun bootstrapClientEvents() {
    ClientPreAttackCallback.EVENT.register { _, _, clickCount ->
        if(clickCount != 0) // ensure client just pressed the button
            EmpyreanNetworking.EMPYREAN_CHANNEL.clientHandle().send(ServerboundLeftClickPacket())
        false
    }
}