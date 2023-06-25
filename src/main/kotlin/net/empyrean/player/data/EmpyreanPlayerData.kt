package net.empyrean.player.data

import kotlinx.serialization.Serializable
import net.empyrean.player.Stats

@Serializable
data class EmpyreanPlayerData(
    var currentMana: Float = 100f,
    var currentHealth: Float = 0f,
    var statistics: Stats = Stats.prefill()
) {
    companion object {
        fun default() = EmpyreanPlayerData(
            currentMana = 0f,
            currentHealth = 100f,
            statistics = Stats.prefill()
        )
    }
}