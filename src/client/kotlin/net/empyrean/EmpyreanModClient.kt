package net.empyrean

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.empyrean.config.ClientConfig
import net.empyrean.event.client.bootstrapClientEvents
import net.empyrean.network.client.bootstrapClientNetworking
import net.fabricmc.api.ClientModInitializer

object EmpyreanModClient : ClientModInitializer {
	@JvmStatic
	val config: ClientConfig get() = AutoConfig.getConfigHolder(ClientConfig::class.java).config

	override fun onInitializeClient() {
		bootstrapClientNetworking()
		bootstrapClientEvents()

		// register config
		AutoConfig.register(ClientConfig::class.java) { cfg, cfgClass -> Toml4jConfigSerializer(cfg, cfgClass) }
	}
}