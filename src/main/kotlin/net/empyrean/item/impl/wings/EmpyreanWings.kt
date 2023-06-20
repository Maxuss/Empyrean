package net.empyrean.item.impl.wings

import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.TrinketItem
import net.empyrean.item.EmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.impl.trinket.DelegatedTrinketRenderer
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.movement.wings.GlidingEntity
import net.empyrean.movement.wings.WingUtil
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.Vec3


open class EmpyreanWings(
    settings: Properties,
    override val itemRarity: ItemRarity,
    val wingData: WingProperties
): TrinketItem(settings), EmpyreanItem, DelegatedTrinketRenderer {
    override val rendererClassName: String = "net.empyrean.render.item.WingTrinketRenderer"
    override val itemKind: ItemKind = ItemKind.WINGS

    override fun data(stack: ItemStack): ItemData {
        return ItemData(stack)
    }

    private fun canContinueFlying(player: Player, stack: ItemStack): Boolean {
        if(WingUtil.isUnusable(player))
            return false
        val age = WingUtil.tickAge(player)
        val wings = (stack.item as EmpyreanWings).wingData
        if(age > wings.flyHeight)
            return false
        val deltaY = WingUtil.deltaY(player)
        return deltaY <= wings.flyHeight
    }

    override fun tick(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        if (entity !is Player)
            return
        if (entity.isFallFlying) {
            if(!canContinueFlying(entity, stack)) {
                WingUtil.stopFlying(entity)
                WingUtil.markUnusable(entity)
                return
            }
            // WHY THE HELL IS FORWARD SPEED NAMED ZZA???
            if (entity.zza > 0) WingUtil.applyWingSpeed(entity, (stack.item as EmpyreanWings).wingData)
            // TODO: wings with special abilities underwater (kind of like fishron wings)
            if (entity.isShiftKeyDown || entity.isUnderWater) WingUtil.stopFlying(entity)
        } else {
            if (entity.onGround() || entity.wasTouchingWater) {
                (entity as GlidingEntity).isSlowFalling = false
                WingUtil.markUsable(entity)
                WingUtil.cleanAgeAndDelta(entity)
            }
            if ((entity as GlidingEntity).isSlowFalling) {
                entity.fallDistance = 0f
                entity.deltaMovement = Vec3(entity.deltaMovement.x, -0.4, entity.deltaMovement.z)
            }
        }
    }
}

data class WingProperties(
    /**
     * Maximum height in blocks that player can ascend to with these wings
     */
    val flyHeight: Float,
    /**
     * Duration in partial ticks that player can fly during with these wings
     */
    val flyDuration: Float,
    /**
     * Horizontal flying speed multiplier
     */
    val horizontalSpeed: Float,
    /**
     * Vertical flying speed multiplier
     */
    val verticalSpeed: Float,
    /**
     * Diving speed multiplier (player is looking down)
     */
    val diveSpeed: Float = 1f,
    /**
     * Ascending speed multiplier (player is looking up)
     */
    val ascensionSpeed: Float = 1f,
    /**
     * Descending speed multiplier (player is gliding after finishing using wings)
     */
    val descendSpeed: Float = 1f
)