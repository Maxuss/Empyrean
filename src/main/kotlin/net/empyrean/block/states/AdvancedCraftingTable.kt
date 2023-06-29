package net.empyrean.block.states

import net.empyrean.block.SimpleEmpyreanBlock
import net.empyrean.item.rarity.ItemRarity
import net.empyrean.menu.handlers.ACTMenuHandler
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

@Suppress("OVERRIDE_DEPRECATION") // why is it deprecated??
class AdvancedCraftingTable: SimpleEmpyreanBlock(
    FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE).strength(3f), ItemRarity.RARE
) {
    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
    ): InteractionResult {
        if(level.isClientSide)
            return InteractionResult.SUCCESS
        player.openMenu(state.getMenuProvider(level, pos))
        player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE)
        return InteractionResult.SUCCESS
    }

    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos): MenuProvider {
        return SimpleMenuProvider({ i, inv, _ ->
            ACTMenuHandler(i, inv, ContainerLevelAccess.create(level, pos))
        }, TITLE)
    }

    companion object {
        val TITLE = Component.translatable("container.empyrean.advanced_crafting_table")
    }
}