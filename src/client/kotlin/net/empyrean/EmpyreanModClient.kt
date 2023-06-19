package net.empyrean

import net.empyrean.config.ClientConfig
import net.empyrean.event.client.bootstrapClientEvents
import net.empyrean.network.client.bootstrapClientNetworking
import net.fabricmc.api.ClientModInitializer
import net.minecraft.Util
import kotlin.random.Random

object EmpyreanModClient : ClientModInitializer {
	@JvmStatic
	val clientRandom: Random = Random(Util.getNanos())

	const val MODID = "empyrean"

	override fun onInitializeClient() {
		ClientConfig.init()

		bootstrapClientNetworking()
		bootstrapClientEvents()
	}
}