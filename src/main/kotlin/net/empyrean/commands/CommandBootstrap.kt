package net.empyrean.commands

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

fun bootstrapCommands() {
    CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
        dispatcher.register(testCommand())
        dispatcher.register(oreCommand())
        dispatcher.register(gameStateCommand())
        dispatcher.register(prefixCommand())
    }
}