package net.empyrean.components

import net.empyrean.player.data.EmpyreanPlayerData
import net.minecraft.world.entity.player.Player

var Player.data: EmpyreanPlayerData
    get() = EmpyreanComponents.PLAYER_DATA[this].playerData
    set(value) { EmpyreanComponents.PLAYER_DATA[this].playerData = value }