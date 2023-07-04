package net.empyrean.effect

import net.empyrean.client.clientData
import net.empyrean.components.data
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import kotlin.math.min

class BossPresenceEffect: EmpyreanEffect(MobEffectCategory.NEUTRAL, 0xFFFFFF) {
    override fun applyEffectTick(player: LivingEntity, amplifier: Int) {
        if(player !is Player)
            return
        if(player.level().isClientSide) {
            player.clientData.isInBossPresence = true
        }
        player.data.adrenalineLevel = min(1f, player.data.adrenalineLevel + (1 / 400f)) // full charge in 20 seconds
    }

    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        return true
    }
}