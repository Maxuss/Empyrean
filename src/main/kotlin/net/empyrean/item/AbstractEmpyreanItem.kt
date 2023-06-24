@file:Suppress("MemberVisibilityCanBePrivate")

package net.empyrean.item

import net.empyrean.item.kind.ItemKind
import net.empyrean.item.properties.EmpyreanItemProperties
import net.empyrean.item.rarity.ItemRarity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

abstract class AbstractEmpyreanItem(
    properties: FabricItemSettings,
    override val itemRarity: ItemRarity,
    override val itemKind: ItemKind,
    override val empyreanProperties: EmpyreanItemProperties = EmpyreanItemProperties()
) : Item(properties), EmpyreanItem {
    override fun tooltip(
        stack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        // noop by default
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        EmpyreanItem.appendHoverText(this, stack, level, tooltipComponents, isAdvanced)
    }

    override fun getName(stack: ItemStack): Component {
        return EmpyreanItem.getName(this, stack)
    }

    protected fun pass(player: ServerPlayer): InteractionResultHolder<ItemStack> {
        return InteractionResultHolder.pass(player.getItemInHand(InteractionHand.MAIN_HAND))
    }

    protected fun success(player: ServerPlayer): InteractionResultHolder<ItemStack> {
        return InteractionResultHolder.success(player.getItemInHand(InteractionHand.MAIN_HAND))
    }

    open fun leftClick(level: Level, player: ServerPlayer) {
        // no-op by default
    }

    open fun rightClick(level: Level, player: ServerPlayer): InteractionResultHolder<ItemStack> {
        return pass(player)
    }

    open fun rightClickOffHand(level: Level, player: ServerPlayer): InteractionResultHolder<ItemStack> {
        return InteractionResultHolder.pass(player.offhandItem)
    }

    override fun verifyTagAfterLoad(compoundTag: CompoundTag) {
        if (!compoundTag.contains("empyrean")) {
            compoundTag.put("empyrean", CompoundTag())
        }
    }

    final override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        if (level.isClientSide) // only handling clicks on server
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand))
        return if (interactionHand == InteractionHand.MAIN_HAND)
            rightClick(level, player as ServerPlayer)
        else
            rightClickOffHand(level, player as ServerPlayer)
    }
}
