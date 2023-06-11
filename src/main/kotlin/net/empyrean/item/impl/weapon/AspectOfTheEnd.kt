package net.empyrean.item.impl.weapon

import net.empyrean.item.BaseEmpyreanItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class AspectOfTheEnd: BaseEmpyreanItem(FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.EPIC)) {
    override fun getName(itemStack: ItemStack): Component {
        return Component.literal("Aspect of the End")
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        list.add(Component.literal("This is AOTE"))
    }

    override fun leftClick(level: Level, player: ServerPlayer) {
        println("JUST LEFT CLICKED")
    }

    override fun rightClick(level: Level, player: ServerPlayer): InteractionResultHolder<ItemStack> {
        println("JUST RIGHT CLICKED")
        return super.rightClick(level, player)
    }
}