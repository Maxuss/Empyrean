package net.empyrean.game

import dev.emi.trinkets.api.TrinketsApi
import net.empyrean.components.EmpyreanComponents
import net.empyrean.components.PlayerDataComponent
import net.empyrean.components.data
import net.empyrean.player.PlayerStat
import net.empyrean.player.Stats
import net.empyrean.util.merged
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.ai.attributes.Attributes
import kotlin.jvm.optionals.getOrDefault
import kotlin.math.min

object GameManager: ServerLifecycleEvents.ServerStarted, ServerTickEvents.EndTick {
    var gameData: GameData = GameData.default()
    lateinit var server: MinecraftServer; private set

    fun init() {
        gameData = GameData.load(FabricLoader.getInstance().gameDir)
    }

    private fun operatePlayers() {
        for(player in PlayerLookup.all(server)) {
            val data = EmpyreanComponents.PLAYER_DATA[player]
            recalculateSinglePlayerStats(player, data)
            tickSinglePlayer(player)
        }
    }

    private fun tickSinglePlayer(player: ServerPlayer) {
        val maxMana = player.data.statistics[PlayerStat.MAX_MANA]
        val manaRegen = player.data.statistics[PlayerStat.MANA_REGEN]
        val curMana = player.data.currentMana
        if(curMana == maxMana)
            return
        player.data.currentMana = min(curMana + manaRegen / 20f, maxMana)
    }

    private fun recalculateSinglePlayerStats(player: ServerPlayer, data: PlayerDataComponent) {
        val mainHand = player.mainHandItem.let(ItemManager::extractItemStats)
        val offHand = player.mainHandItem.let(ItemManager::extractItemStats)
        val armor = player.armorSlots.map(ItemManager::extractItemStats).merged()
        val trinkets = TrinketsApi.getTrinketComponent(player).map { component -> component.allEquipped.map { ItemManager.extractItemStats(it.b) }.merged() }.getOrDefault(Stats.empty())

        val itemStats = mainHand.mergeMany(offHand, armor, trinkets)
        val playerStats = data.rawStats
        val merged = playerStats.merge(itemStats)
        if(merged != data.statistics)
            data.statistics = merged
        val movSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED)
        movSpeed!!.baseValue = data.statistics[PlayerStat.MOVEMENT_SPEED].toDouble()
    }

    override fun onServerStarted(server: MinecraftServer) {
        this.server = server
    }

    override fun onEndTick(server: MinecraftServer) {
        operatePlayers()
    }
}