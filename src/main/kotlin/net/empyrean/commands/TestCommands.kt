package net.empyrean.commands

import net.empyrean.chat.SpecialFormatting
import net.empyrean.chat.withEmpyreanStyle
import net.empyrean.commands.api.command
import net.minecraft.network.chat.Component

fun testCommand() = command("empyrean") {
    "test" runs {
        source.player?.sendSystemMessage(Component.literal("Hello, empyrean world!").withEmpyreanStyle(SpecialFormatting.EMPYREAN_L_STARLIKE))
    }
}