package net.empyrean.components

import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import kotlinx.serialization.Serializable
import net.empyrean.nbt.decodeNbt
import net.empyrean.nbt.encodeNbtTo
import net.empyrean.network.decodePacket
import net.empyrean.network.encodePacket
import net.empyrean.player.Stats
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

@Suppress("UnstableApiUsage")
class PlayerDataComponent(val provider: Player) : PlayerComponent<Component>, AutoSyncedComponent {
    var currentMana: Float by autosync(100f)
    var currentHealth: Float by autosync(100f)
    var rawStats: Stats by autosync(Stats.prefill())
    var statistics: Stats by autosync(Stats.prefill())

    override fun readFromNbt(tag: CompoundTag) {
        if (tag.isEmpty)
            return // migration support
        val decoded: PlayerDataHolder = decodeNbt(tag)
        applyChanges(decoded)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> unsyncSet(property: KProperty1<PlayerDataComponent, T>, value: T) {
        val autosync = property.getDelegate(this) as? AutoSyncProperty<T> ?: return
        autosync.unsyncSet(value)
    }

    override fun writeToNbt(tag: CompoundTag) {
        encodeNbtTo(PlayerDataHolder(currentMana, currentHealth, rawStats, statistics), tag)
    }

    override fun writeSyncPacket(buf: FriendlyByteBuf, recipient: ServerPlayer?) {
        encodePacket(PlayerDataHolder(currentMana, currentHealth, rawStats, statistics), buf)
    }

    override fun applySyncPacket(buf: FriendlyByteBuf) {
        val decoded: PlayerDataHolder = decodePacket(buf)
        applyChanges(decoded)
    }

    private fun applyChanges(holder: PlayerDataHolder) {
        currentMana = holder.currentMana
        currentHealth = holder.currentHealth
        rawStats = holder.rawStats
        statistics = holder.statistics
    }

    override fun shouldSyncWith(player: ServerPlayer?): Boolean {
        return true
    }

    fun sync() {
        EmpyreanComponents.PLAYER_DATA.sync(provider)
    }

    private fun <T> autosync(default: T): AutoSyncProperty<T> {
        return AutoSyncProperty(default)
    }
}

@Serializable
data class PlayerDataHolder(
    val currentMana: Float,
    val currentHealth: Float,
    val rawStats: Stats,
    val statistics: Stats,
)

data class AutoSyncProperty<T>(
    var value: T
) {
    operator fun getValue(self: Any, prop: KProperty<*>): T {
        return value
    }

    operator fun setValue(self: PlayerDataComponent, prop: KProperty<*>, value: T) {
        this.value = value
        if(!self.provider.isLocalPlayer)
            self.sync() // only synchronize if server data was mutated
    }

    fun unsyncSet(value: T) {
        this.value = value
    }
}