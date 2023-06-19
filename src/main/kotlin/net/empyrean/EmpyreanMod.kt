package net.empyrean

import net.empyrean.commands.bootstrapCommands
import net.empyrean.events.bootstrapEvents
import net.empyrean.item.bootstrapItems
import net.empyrean.network.bootstrapNetworking
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom

object EmpyreanMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")
    const val modId: String = "empyrean"

    val serverRandom = kotlin.random.Random(ThreadLocalRandom.current().nextInt())

    override fun onInitialize() {
        bootstrapItems()
        bootstrapCommands()
        bootstrapNetworking()
        bootstrapEvents()
    }
}