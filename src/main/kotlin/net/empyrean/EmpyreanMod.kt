package net.empyrean

import net.empyrean.block.bootstrapBlocks
import net.empyrean.commands.bootstrapCommands
import net.empyrean.events.bootstrapEvents
import net.empyrean.game.GameManager
import net.empyrean.item.bootstrapItems
import net.empyrean.network.bootstrapNetworking
import net.empyrean.worldgen.bootstrapOrePlacement
import net.fabricmc.api.ModInitializer
import net.minecraft.util.RandomSource
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom

object EmpyreanMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")
    const val modId: String = "empyrean"

    val serverRandom = kotlin.random.Random(ThreadLocalRandom.current().nextInt())
    val serverRandomSource = RandomSource.create(serverRandom.nextLong())

    override fun onInitialize() {
        preloadClasses()

        bootstrapItems()
        bootstrapBlocks()
        bootstrapCommands()
        bootstrapNetworking()
        bootstrapEvents()
        bootstrapOrePlacement()

        GameManager.init()
    }

    /**
     * Preloads necessary classes
      */
    private fun preloadClasses() {
        Class.forName("net.empyrean.registry.EmpyreanRegistries")
        Class.forName("net.empyrean.item.prefix.EmpyreanPrefixes")
    }
}