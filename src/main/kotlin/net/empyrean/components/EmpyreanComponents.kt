package net.empyrean.components

import net.minecraft.world.entity.player.Player

val Player.data: PlayerDataComponent
    get() = EmpyreanComponents.PLAYER_DATA[this]