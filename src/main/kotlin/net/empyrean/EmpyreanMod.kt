package net.empyrean

import kotlinx.serialization.Serializable
import net.empyrean.commands.bootstrapCommands
import net.empyrean.events.bootstrapEvents
import net.empyrean.item.bootstrapItems
import net.empyrean.nbt.Identifier
import net.empyrean.nbt.SerId
import net.empyrean.network.bootstrapNetworking
import net.empyrean.network.decodePacket
import net.empyrean.network.encodePacket
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom

object EmpyreanMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")
	const val modId: String = "empyrean"

	val serverRandom = kotlin.random.Random(ThreadLocalRandom.current().nextInt())

	override fun onInitialize() {
		logger.info("Hello Fabric world!")

		bootstrapItems()
		bootstrapCommands()
		bootstrapNetworking()
		bootstrapEvents()

		val exampleData = ExampleClass("Hello, World!", Identifier("empyrean:test_data"))
		println("BEFORE SER $exampleData")
		val buf = PacketByteBufs.create()
		encodePacket(exampleData, buf)
		println("SER: ${buf.nioBuffer().array()}")
		println("SER: ${String(buf.nioBuffer().array())}")
		val out = decodePacket<ExampleClass>(buf)
		println("AFTER DE $out")
	}

	@Serializable
	data class ExampleClass(val firstValue: String, val secondValue: Identifier, val uuid: SerId = SerId.randomUUID(), val integer: Int = serverRandom.nextInt(), val float: Float = serverRandom.nextFloat())
}