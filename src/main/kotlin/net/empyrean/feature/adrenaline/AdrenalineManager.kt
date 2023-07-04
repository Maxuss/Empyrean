package net.empyrean.feature.adrenaline

import net.empyrean.client.clientData
import net.empyrean.components.data
import net.empyrean.effect.EmpyreanEffects
import net.empyrean.events.EmpyreanDamageEvents
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.player.Player

object AdrenalineManager: EmpyreanDamageEvents.ServerPlayerDamaged, EmpyreanDamageEvents.ClientPlayerDamaged {
    override fun onDamage(player: ServerPlayer, damage: Float, source: DamageSource) {
        if(!player.hasEffect(EmpyreanEffects.BOSS_PRESENCE))
            return // player can not accumulate adrenaline
        player.data.adrenalineLevel = 0f
    }

    override fun onDamageClient(player: Player, damage: Float, source: DamageSource) {
        if(!player.hasEffect(EmpyreanEffects.BOSS_PRESENCE))
            return // player can not accumulate adrenaline
        player.clientData.justLostAdrenaline = true
    }
}