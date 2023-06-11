package net.empyrean

import net.empyrean.config.client.ClientConfig
import net.empyrean.event.client.bootstrapClientEvents
import net.empyrean.network.client.bootstrapClientNetworking
import net.fabricmc.api.ClientModInitializer

object EmpyreanModClient : ClientModInitializer {
	val config: ClientConfig = ClientConfig.createAndLoad()

	override fun onInitializeClient() {
		bootstrapClientNetworking()
		bootstrapClientEvents()
	}
}