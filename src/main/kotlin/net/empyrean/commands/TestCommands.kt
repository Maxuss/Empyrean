package net.empyrean.commands

import net.empyrean.commands.api.EmpyreanCommandException
import net.empyrean.commands.api.command
import net.empyrean.commands.api.suggests
import net.minecraft.network.chat.Component

fun testCommand() = command("empyrean") {
    "test" {
        argString("string", items = suggests("string") { listOf("Hello", "world") }) { string ->

            this runs {
                source.player?.sendSystemMessage(Component.literal("Just ${string()}"))
                throw EmpyreanCommandException("An exception happened ${string()}")
            }

            argIdentifier("id", suggests("id") { listOf("empyrean:test", "minecraft:another") }) runs { id ->
                source.player?.sendSystemMessage(Component.literal("String and ID: ${string()} ${id()}"))
            }
        }

        argInt("number", -25..25) runs { number ->
            source.player?.sendSystemMessage(Component.literal("Just number ${number()}"))
        }
    }
}