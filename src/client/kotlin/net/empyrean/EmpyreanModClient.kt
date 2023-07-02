package net.empyrean

import net.empyrean.commands.bootstrapClientCommands
import net.empyrean.config.ClientConfig
import net.empyrean.event.client.bootstrapClientEvents
import net.empyrean.item.client.bootstrapClientItems
import net.empyrean.network.client.bootstrapClientNetworking
import net.empyrean.registry.client.bootstrapClientRegistries
import net.fabricmc.api.ClientModInitializer
import net.minecraft.Util
import kotlin.random.Random

object EmpyreanModClient : ClientModInitializer {
    @JvmStatic
    val clientRandom: Random = Random(Util.getNanos())

    const val MODID = "empyrean"

    override fun onInitializeClient() {
        ClientConfig.init()

        preloadNecessaryClasses()

        bootstrapClientNetworking()
        bootstrapClientEvents()
        bootstrapClientItems()
        bootstrapClientRegistries()
        bootstrapClientCommands()
    }

    private fun preloadNecessaryClasses() {
        Class.forName("net.empyrean.gui.screen.EmpyreanScreens")
    }
}