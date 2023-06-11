package net.empyrean

import net.empyrean.network.client.bootstrapClientNetworking
import net.fabricmc.api.ClientModInitializer

object EmpyreanModClient : ClientModInitializer {
	override fun onInitializeClient() {
		bootstrapClientNetworking()
	}
}