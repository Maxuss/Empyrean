package net.empyrean.client

import net.minecraft.world.entity.player.Player

class PlayerClientData {
    var isInBossPresence: Boolean = false
    var justLostAdrenaline: Boolean = false
}

val Player.clientData get() = (this as PlayerClientDataAccessor).getClientData()