package net.empyrean.network.packets.clientbound

import net.minecraft.network.chat.Component


@JvmRecord
data class ClientboundStatusMessagePacket(val message: Component, val delayFrames: Int)
