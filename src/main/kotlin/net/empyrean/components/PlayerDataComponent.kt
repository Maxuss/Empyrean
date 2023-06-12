package net.empyrean.components

import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import net.empyrean.nbt.decodeNbt
import net.empyrean.nbt.encodeNbtTo
import net.empyrean.network.decodePacket
import net.empyrean.network.encodePacket
import net.empyrean.player.data.EmpyreanPlayerData
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

@Suppress("UnstableApiUsage")
class PlayerDataComponent(private val provider: Player): PlayerComponent<Component>, AutoSyncedComponent {
    @Suppress("MemberVisibilityCanBePrivate")
    var playerData: EmpyreanPlayerData = EmpyreanPlayerData.default()

    override fun readFromNbt(tag: CompoundTag) {
        if(tag.isEmpty)
            return // migration support
        playerData = decodeNbt(tag)
    }

    override fun writeToNbt(tag: CompoundTag) {
        encodeNbtTo(playerData, tag)
    }

    override fun writeSyncPacket(buf: FriendlyByteBuf, recipient: ServerPlayer?) {
        println("WRITING SYNC PACKET")
        encodePacket(playerData, buf)
    }

    override fun applySyncPacket(buf: FriendlyByteBuf) {
        playerData = decodePacket(buf)
    }
}