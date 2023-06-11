package net.empyrean.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component

fun testCommand(): LiteralArgumentBuilder<CommandSourceStack> =
    LiteralArgumentBuilder.literal<CommandSourceStack?>("test")
        .executes {
            it.source.sendSystemMessage(Component.literal("Hello, world!"))
            0
        }
