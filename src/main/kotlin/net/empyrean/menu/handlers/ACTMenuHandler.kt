package net.empyrean.menu.handlers

import net.empyrean.block.EmpyreanBlocks
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.inventory.CraftingMenu
import net.minecraft.world.item.crafting.Recipe

class ACTMenuHandler(syncId: Int, playerInv: Inventory, cla: ContainerLevelAccess): CraftingMenu(syncId, playerInv, cla) {
    private val actAccess: ContainerLevelAccess
    init {
        actAccess = cla
    }

    constructor(syncId: Int, playerInv: Inventory): this(syncId, playerInv, ContainerLevelAccess.NULL)

    override fun stillValid(player: Player): Boolean {
        return stillValid(actAccess, player, EmpyreanBlocks.ADVANCED_CRAFTING_TABLE)
    }

    override fun recipeMatches(recipe: Recipe<in CraftingContainer>): Boolean {
        return super.recipeMatches(recipe)
    }
}