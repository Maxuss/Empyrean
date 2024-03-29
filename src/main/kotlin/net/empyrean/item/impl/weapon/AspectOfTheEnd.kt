package net.empyrean.item.impl.weapon

import net.empyrean.item.AbstractEmpyreanItem
import net.empyrean.item.data.ItemData
import net.empyrean.item.kind.ItemKind
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.player.Stats
import net.empyrean.player.mana
import net.empyrean.util.entity.raycast
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class AspectOfTheEnd : AbstractEmpyreanItem(
    FabricItemSettings().fireproof().maxCount(1),
    ItemRarity.SMOLDERING,
    ItemKind.SWORD,
    Stats.of {
        damageMelee = 25

        maxMana = 20
        manaRegen = 1
        healthRegen = 0.5
        jumpHeight = 0.5
        acceleration = 0.2
        damageReduction = 0.05
    }
    ) {

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