@file:Suppress("MemberVisibilityCanBePrivate")

package net.empyrean.item

import net.empyrean.chat.withColor
import net.empyrean.gui.text.color.EmpyreanColor
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

abstract class EmpyreanItem(
    properties: FabricItemSettings,
    val itemRarity: ItemRarity,
    val itemKind: ItemKind
): Item(properties) {
    open fun tooltip(
        itemStack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        // noop by default
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        tooltip(stack, level, tooltipComponents, isAdvanced)
        tooltipComponents.add(Component.empty())
        val color = itemRarity.color as EmpyreanColor
        tooltipComponents.add(Component.literal("${itemRarity.named} ${itemKind.named}").withStyle(Style.EMPTY.withBold(true)).withColor(color))
    }

    override fun getName(stack: ItemStack): Component {
        return (super.getName(stack) as MutableComponent).withColor(itemRarity.color as EmpyreanColor)
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

    abstract fun data(stack: ItemStack): ItemData

    final override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        if(level.isClientSide) // only handling clicks on server
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand))
        return if(interactionHand == InteractionHand.MAIN_HAND)
            rightClick(level, player as ServerPlayer)
        else
            rightClickOffHand(level, player as ServerPlayer)
    }

    override fun verifyTagAfterLoad(compoundTag: CompoundTag) {
        super.verifyTagAfterLoad(compoundTag)
        if(!compoundTag.contains("empyrean")) {
            compoundTag.put("empyrean", CompoundTag())
        }
    }
}
