package net.empyrean.player

import kotlinx.serialization.Serializable
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.server.level.ServerPlayer

@Serializable
enum class PlayerRank(val prefix: Component) {
    NONE(Component.empty()),
    ADMIN(Component.literal("[ADMIN]").withStyle(Style.EMPTY.withColor(0x752ef7)));

    fun formatChatMessage(player: ServerPlayer, msg: Component): Component {
        return prefix.plainCopy().append(Component.literal(" ")).append(player.name).append(Component.literal(": ")).append(msg)
    }
}