package net.empyrean.item.impl.weapon

import net.empyrean.item.AbstractEmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.player.Stats
import net.empyrean.util.text.CommonComponents
import net.empyrean.util.text.Text
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class MetastaticAxe: AbstractEmpyreanItem(
    FabricItemSettings().maxCount(1),
    ItemRarity.STARLIKE,
    ItemKind.SWORD,
    Stats.of {
        damageMelee = 80
        damageReduction = .1
        attackSpeed = .15f
    }
) {
    override fun data(stack: ItemStack): ItemData {
        return ItemData(stack)
    }

    override fun tooltip(stack: ItemStack, level: Level?, list: MutableList<Component>, isAdvanced: TooltipFlag) {
        super.tooltip(stack, level, list, isAdvanced)
        list.add(Text.concat(
            CommonComponents.MELEE_ABILITY,
            Text.of("- Throw").withStyle(ChatFormatting.GRAY),
            CommonComponents.RIGHT_CLICK,
        ))

        list.add(Text.of("Throw three phantasmal copies of your").withStyle(ChatFormatting.GRAY))
        list.add(Text.of("axe at your foes. Each axe").withStyle(ChatFormatting.GRAY))
        list.add(Text.concat(
            Text.of("deals").withStyle(ChatFormatting.GRAY),
            Text.of("30%").withStyle(ChatFormatting.RED),
            Text.of("of your melee").withStyle(ChatFormatting.GRAY)
        ))
        list.add(Text.of("damage.").withStyle(ChatFormatting.GRAY))
        list.add(CommonComponents.abilityRequiresToken("Melee"))
    }
}