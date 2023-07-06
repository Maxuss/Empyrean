package net.empyrean.network.packets.serverbound

@JvmRecord
data class ServerboundPlayerActionPacket(val action: Action) {
    enum class Action {
        LEFT_CLICK,
        ACTIVATE_ADRENALINE
    }
}