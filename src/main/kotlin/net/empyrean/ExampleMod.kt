package net.empyrean

import kotlinx.serialization.Serializable
import net.empyrean.nbt.Identifier
import net.empyrean.nbt.SerId
import net.empyrean.nbt.decodeNbt
import net.empyrean.nbt.encodeNbt
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object ExampleMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")

		val value = ExampleClass("Hello, World!", Identifier("empyrean:example"))
		logger.info("Data: {}", value)
		val encoded = encodeNbt(value)
		logger.info("Encoded: {}", encoded)
		val decoded = decodeNbt<ExampleClass>(encoded)
		logger.info("Decoded: {}", decoded)
	}

	@Serializable
	data class ExampleClass(
		val name: String,
		val id: Identifier,
		val randomId: SerId = SerId.randomUUID()
	)
}