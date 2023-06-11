package net.empyrean

import net.empyrean.commands.bootstrapCommands
import net.empyrean.events.bootstrapEvents
import net.empyrean.item.bootstrapItems
import net.empyrean.network.bootstrapNetworking
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object EmpyreanMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")
	const val modId: String = "empyrean"

	override fun onInitialize() {
		logger.info("Hello Fabric world!")

		bootstrapItems()
		bootstrapCommands()
		bootstrapNetworking()
		bootstrapEvents()

	}
}