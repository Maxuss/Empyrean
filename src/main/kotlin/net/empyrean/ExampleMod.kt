package net.empyrean

import net.empyrean.commands.bootstrapCommands
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object ExampleMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")

	override fun onInitialize() {
		logger.info("Hello Fabric world!")

		bootstrapCommands()
	}
}