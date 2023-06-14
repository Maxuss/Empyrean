@file:Suppress("MemberVisibilityCanBePrivate")

package net.empyrean.item

import net.empyrean.item.data.ItemData
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

abstract class EmpyreanItem(
    properties: FabricItemSettings
): Item(properties) {
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
