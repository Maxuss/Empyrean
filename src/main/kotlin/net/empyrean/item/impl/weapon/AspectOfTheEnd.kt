package net.empyrean.item.impl.weapon

import net.empyrean.chat.SpecialFormatting
import net.empyrean.chat.withEmpyreanStyle
import net.empyrean.item.EmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.player.mana
import net.empyrean.util.entity.raycast
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class AspectOfTheEnd: EmpyreanItem(FabricItemSettings().fireproof().maxCount(1)) {
    override fun getName(itemStack: ItemStack): Component {
        return Component.literal("Aspect of the End").withEmpyreanStyle(SpecialFormatting.EMPYREAN_L_NAUTICAL)
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        list.add(Component.literal("This is AOTE"))
    }

    override fun rightClick(level: Level, player: ServerPlayer): InteractionResultHolder<ItemStack> {
        player.mana -= 50
        val result = raycast(player, 5)
        player.teleportTo(result.x, result.y, result.z)
        return success(player)
    }

    override fun data(stack: ItemStack): ItemData {
        return ItemData(stack)
    }
}