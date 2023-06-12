package net.empyrean.player.data

import kotlinx.serialization.Serializable

@Serializable
class EmpyreanPlayerData(
    var currentMana: Float
) {
    companion object {
        fun default() = EmpyreanPlayerData(
            currentMana = 0f
        )
    }
}