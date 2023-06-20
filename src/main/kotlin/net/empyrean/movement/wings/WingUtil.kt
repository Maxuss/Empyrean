package net.empyrean.movement.wings

import dev.emi.trinkets.api.TrinketComponent
import dev.emi.trinkets.api.TrinketsApi
import net.empyrean.EmpyreanMod
import net.empyrean.item.impl.wings.EmpyreanWings
import net.empyrean.item.impl.wings.WingProperties
import net.empyrean.nbt.Identifier
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.TagKey
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.phys.Vec3
import java.util.*
import java.util.concurrent.ConcurrentHashMap


object WingUtil {
    private val INFINITE_FLIGHT: TagKey<Item> =
        TagKey.create(Registries.ITEM, Identifier(EmpyreanMod.modId, "infinite_flight"))
    private val entityFlyingAge: ConcurrentHashMap<UUID, Float> = ConcurrentHashMap()
    private val entityFlyingStartY: ConcurrentHashMap<UUID, Float> = ConcurrentHashMap()
    private val unusable: MutableSet<UUID> = ConcurrentHashMap.newKeySet()

    @JvmStatic
    fun adjustPitch(entity: Entity, value: Float): Float {
        var currentPitch: Float = value
        if (entity is ServerPlayer && entity.isFallFlying) {
            val component: Optional<TrinketComponent> = TrinketsApi.getTrinketComponent(entity)
            component.ifPresent { trinketComponent ->
                if (trinketComponent.isEquipped { stack -> stack.item is EmpyreanWings })
                    currentPitch = Mth.wrapDegrees(entity.xRot)
            }
        }
        return currentPitch
    }

    @JvmStatic
    fun applyWingSpeed(player: Player, wings: WingProperties) {
        (player as GlidingEntity).isSlowFalling = false
        val rotation: Vec3 = player.lookAngle
        val velocity: Vec3 = player.deltaMovement
        val speed = if(player.xRot < -55 && player.xRot > -105) {
            1.2f * wings.ascensionSpeed * 0.2f // ascending here
        } else if(player.xRot > 60 && player.xRot < 100) {
            1.4f * wings.diveSpeed * 0.04f // diving here
        } else ((wings.diveSpeed + wings.ascensionSpeed) / 2f) * 0.01f
        player.addDeltaMovement(
            Vec3(
                speed * wings.horizontalSpeed * (rotation.x + (rotation.x * 1.5 - velocity.x)),
                speed * wings.verticalSpeed * (rotation.y + (rotation.y * 1.5 - velocity.y)),
                  wings.horizontalSpeed * speed * (rotation.z + (rotation.z * 1.5 - velocity.z)),
                )
        )
    }

    fun cleanAgeAndDelta(player: Player) {
        entityFlyingAge.remove(player.uuid)
        entityFlyingStartY.remove(player.uuid)
    }

    fun markUnusable(player: Player) {
        unusable.add(player.uuid)
    }

    fun isUnusable(player: Player): Boolean {
        return unusable.contains(player.uuid)
    }

    fun markUsable(player: Player) {
        unusable.remove(player.uuid)
        entityFlyingAge.remove(player.uuid)
    }

    fun deltaY(player: Player): Float {
        if(!entityFlyingStartY.containsKey(player.uuid))
            entityFlyingStartY[player.uuid] = player.y.toFloat()
        return player.y.toFloat() - entityFlyingStartY[player.uuid]!!
    }

    fun tickAge(forPlayer: Player): Float {
        if(!entityFlyingAge.containsKey(forPlayer.uuid))
            entityFlyingAge[forPlayer.uuid] = -(1f / 4f)
        entityFlyingAge[forPlayer.uuid] = entityFlyingAge[forPlayer.uuid]!! + (1f / 4f)
        return entityFlyingAge[forPlayer.uuid]!!
    }

    fun stopFlying(player: Player) {
        entityFlyingAge.remove(player.uuid)
        entityFlyingStartY.remove(player.uuid)
        (player as GlidingEntity).isSlowFalling = true
        if (player.xRot < -90 || player.xRot > 90) {
            val offset: Float = (if (player.xRot < -90) player.xRot + 180 else player.xRot - 180) * 2
            player.xRot = (if (player.xRot < -90) 180 + offset else -180 - offset) + player.xRot
            player.yRot = 180 + player.yRot
        }
        player.stopFallFlying()
    }
}