package net.empyrean.components

import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import net.empyrean.nbt.decodeNbt
import net.empyrean.nbt.encodeNbt
import net.empyrean.network.decodePacket
import net.empyrean.network.encodePacket
import net.empyrean.player.data.EmpyreanPlayerData
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

@Suppress("UnstableApiUsage")
class PlayerDataComponent(private val provider: Player): PlayerComponent<Component>, AutoSyncedComponent {
    private var playerData: EmpyreanPlayerData? = null

    override fun readFromNbt(tag: CompoundTag) {
        playerData = decodeNbt(tag.getCompound("Empyrean"))
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.put("Empyrean", encodeNbt(playerData))
    }

    override fun writeSyncPacket(buf: FriendlyByteBuf, recipient: ServerPlayer?) {
        encodePacket(this, buf)
    }

    override fun applySyncPacket(buf: FriendlyByteBuf) {
        playerData = decodePacket(buf)
    }
}