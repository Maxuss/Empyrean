package net.empyrean.feature.adrenaline

import net.empyrean.client.clientData
import net.empyrean.components.data
import net.empyrean.effect.EmpyreanEffects
import net.empyrean.events.EmpyreanDamageEvents
import net.empyrean.events.EmpyreanPlayerEvents
import net.empyrean.sound.EmpyreanSounds
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player

fun Player.wasAdrenalineActivated(): Boolean = hasEffect(EmpyreanEffects.ADRENALINE)
fun Player.isAccumulatingAdrenaline(): Boolean = hasEffect(EmpyreanEffects.BOSS_PRESENCE) && !wasAdrenalineActivated()

object AdrenalineManager: EmpyreanDamageEvents.ServerPlayerDamaged, EmpyreanDamageEvents.ClientPlayerDamaged, EmpyreanPlayerEvents.PlayerTick {
    fun tryActivateAdrenaline(player: ServerPlayer) {
        if(player.data.adrenalineLevel < 1f)
            return
        if(player.wasAdrenalineActivated())
            return
        player.addEffect(MobEffectInstance(EmpyreanEffects.ADRENALINE, 140, 1))
        player.level().playSound(
            null,
            player.blockPosition(),
            EmpyreanSounds.ADRENALINE_ACTIVATE,
            SoundSource.PLAYERS,
            4f,
            1f
        )
    }

    override fun onDamage(player: ServerPlayer, damage: Float, source: DamageSource) {
        if(!player.isAccumulatingAdrenaline())
            return // player can not accumulate adrenaline
        player.data.adrenalineLevel = 0f
    }

    override fun onDamageClient(player: Player, damage: Float, source: DamageSource) {
        if(!player.isAccumulatingAdrenaline())
            return // player can not accumulate adrenaline
        player.clientData.justLostAdrenaline = true
    }

    override fun onPlayerTick(player: ServerPlayer) {
        if(!player.hasEffect(EmpyreanEffects.BOSS_PRESENCE) && player.data.adrenalineLevel > 0f) {
            player.data.adrenalineLevel = 0f
        }
    }
}