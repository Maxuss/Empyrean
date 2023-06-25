package net.empyrean.item.impl.wings

import com.google.common.collect.ImmutableList
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.TrinketItem
import net.empyrean.item.EmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.impl.trinket.DelegatedTrinketRenderer
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.properties.EmpyreanItemProperties
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.movement.wings.GlidingEntity
import net.empyrean.movement.wings.WingUtil
import net.empyrean.util.text.Text
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import java.util.*


open class EmpyreanWings(
    settings: Properties,
    override val itemRarity: ItemRarity,
    val wingData: WingProperties,
    override val empyreanProperties: EmpyreanItemProperties = EmpyreanItemProperties()
): TrinketItem(settings), EmpyreanItem, DelegatedTrinketRenderer {
    override val rendererClassName: String = "net.empyrean.render.item.WingTrinketRenderer"
    override val itemKind: ItemKind = ItemKind.WINGS

    override fun tooltip(stack: ItemStack, level: Level?, list: MutableList<Component>, isAdvanced: TooltipFlag) {
        list.add(Text.of("☄ Wings:").withStyle(ChatFormatting.DARK_GRAY))
        list.addAll(wingData.stringify().map { Component.literal(" > ").append(it) })
    }

    override fun getName(stack: ItemStack): Component {
        return EmpyreanItem.getName(this, stack)
    }

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

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        EmpyreanItem.appendHoverText(this, stack, level, tooltipComponents, isAdvanced)

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
) {
    internal fun stringify(): List<Component> {
        val flyHeightString = "${flightHeightModifierNameMap.floorEntry(flyHeight).value} maximum height"
        val flyDurationString = "${flightDurationModifierNameMap.floorEntry(flyDuration).value} flight time"
        val avgSpeed = (horizontalSpeed + verticalSpeed) / 2f
        val flySpeedString = "${speedModifierNameMap.floorEntry(avgSpeed).value} horizontal speed"
        val flyAccelerationString = "${speedModifierNameMap.floorEntry((ascensionSpeed + descendSpeed) / 2f).value} vertical speed"
        return ImmutableList.of(
            Component.literal(flyDurationString).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)),
            Component.literal(flyHeightString).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)),
            Component.literal(flySpeedString).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)),
            Component.literal(flyAccelerationString).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
        )
    }

    companion object {
        private val speedModifierNameMap: TreeMap<Float, String> = TreeMap(mapOf(
            0f to "Horrible",
            0.2f to "Snail-like",
            0.4f to "Low",
            0.7f to "Moderate",
            1.1f to "Great",
            1.3f to "Amazing",
            1.6f to "Insane",
            1.9f to "Ludicrous"
        ))
        private val flightHeightModifierNameMap: TreeMap<Float, String> = TreeMap(mapOf(
            10f to "Nonexistent",
            20f to "Low",
            50f to "Moderate",
            90f to "Great",
            130f to "Insane",
            190f to "Empyrical"
        ))
        private val flightDurationModifierNameMap: TreeMap<Float, String> = TreeMap(mapOf(
            20f to "Microscopic",
            50f to "Very low",
            100f to "Low",
            180f to "Average",
            230f to "Above average",
            300f to "Great",
            390f to "Amazing",
            490f to "Colossal",
            800f to "Empyrical"
        ))
    }
}