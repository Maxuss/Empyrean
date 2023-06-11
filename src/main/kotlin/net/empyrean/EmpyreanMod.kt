package net.empyrean

import net.empyrean.commands.bootstrapCommands
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.bootstrapNetworking
import net.empyrean.network.packets.PacketEmpyreanJoin
import net.empyrean.util.schedule.Scheduler
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.level.ServerPlayer
import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.seconds

object EmpyreanMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")

	override fun onInitialize() {
		logger.info("Hello Fabric world!")

		bootstrapCommands()
		bootstrapNetworking()

		net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.ENTITY_LOAD.register { entity, level ->
			if(level.isClientSide || entity !is ServerPlayer)
				return@register
			val empyreanVersion = FabricLoader.getInstance().getModContainer("empyrean").get().metadata.version.friendlyString
			val handle = EmpyreanNetworking.EMPYREAN_CHANNEL.serverHandle(entity)
			handle.send(PacketEmpyreanJoin(empyreanVersion))

			// wait for authorization
			EmpyreanNetworking.unauthorizedPlayers.add(entity.uuid)
			Scheduler.runTaskLater(1.seconds) {
				if(EmpyreanNetworking.unauthorizedPlayers.contains(entity.uuid)) {
					EmpyreanNetworking.unauthorizedPlayers.remove(entity.uuid)
					entity.disconnect()
				}
			}
		}
	}
}