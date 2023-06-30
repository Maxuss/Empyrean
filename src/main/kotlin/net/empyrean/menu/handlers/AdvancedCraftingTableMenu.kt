package net.empyrean.menu.handlers

import net.empyrean.block.EmpyreanBlocks
import net.empyrean.menu.EmpyreanMenu
import net.empyrean.menu.container.AdvancedCraftingContainer
import net.empyrean.recipe.EmpyreanRecipes
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import kotlin.jvm.optionals.getOrNull

open class AdvancedCraftingTableMenu(syncId: Int, inv: Inventory, cla: ContainerLevelAccess): AbstractContainerMenu(EmpyreanMenu.ADVANCED_CRAFTING_TABLE, syncId) {
    private val craftSlots: CraftingContainer = AdvancedCraftingContainer(this, 3, 3)
    private val resultSlots = ResultContainer()
    private val access: ContainerLevelAccess = cla
    private val player: Player = inv.player

    init {
        this.addSlot(ResultSlot(inv.player, this.craftSlots, this.resultSlots, 0, 124, 35))
        // actual grid
        for(j in 0 until 3) {
            for(k in 0 until 3) {
                addSlot(Slot(craftSlots, k + j * 3, 30 + k * 18, 17 + j * 18))
            }
        }
        // inventory
        for(j in 0 until 3) {
            for(k in 0 until 9) {
                addSlot(Slot(inv, k + j * 9 + 9, 8 + k * 18, 84 + j * 18))
            }
        }
        // hotbar
        for(j in 0 until 9) {
            addSlot(Slot(inv, j, 8 + j * 18, 142))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var itemStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack2 = slot.item
            itemStack = itemStack2.copy()
            if (index == 0) {
                this.access.execute { level: Level?, _: BlockPos? ->
                    level?.let { itemStack2.item.onCraftedBy(itemStack2, it, player) }
                }
                if (!moveItemStackTo(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY
                }
                slot.onQuickCraft(itemStack2, itemStack)
            } else if (if (index in 10..45) !moveItemStackTo(
                    itemStack2,
                    1,
                    10,
                    false
                ) && (if (index < 37) !moveItemStackTo(itemStack2, 37, 46, false) else !moveItemStackTo(
                    itemStack2,
                    10,
                    37,
                    false
                )) else !moveItemStackTo(itemStack2, 10, 46, false)
            ) {
                return ItemStack.EMPTY
            }
            if (itemStack2.isEmpty) {
                slot.setByPlayer(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }
            if (itemStack2.count == itemStack.count) {
                return ItemStack.EMPTY
            }
            slot.onTake(player, itemStack2)
            if (index == 0) {
                player.drop(itemStack2, false)
            }
        }
        return itemStack
    }

    @Suppress("USELESS_CAST") // dumb plugin again
    private fun slotChangedCraftingGrid(
        menu: AbstractContainerMenu,
        level: Level,
        player: Player,
        container: CraftingContainer,
        result: ResultContainer
    ) {
        var itemStack2: ItemStack? = null
        var craftingRecipe: Recipe<CraftingContainer>? = null
        if (level.isClientSide) {
            return
        }
        val serverPlayer = player as ServerPlayer
        var itemStack = ItemStack.EMPTY
        val optional: Recipe<CraftingContainer>? = level.server!!
            .recipeManager.getRecipeFor(EmpyreanRecipes.AdvancedCrafting, container, level).map { it as? Recipe<CraftingContainer> }
            .orElseGet { level.server!!.recipeManager.getRecipeFor(RecipeType.CRAFTING, container, level).getOrNull() }
        if (optional != null && result.setRecipeUsed(
                level,
                serverPlayer,
                optional.also { craftingRecipe = it }) && craftingRecipe!!.assemble(
                container,
                level.registryAccess()
            ).also {
                itemStack2 = it
            }.isItemEnabled(level.enabledFeatures())
        ) {
            itemStack = itemStack2!!
        }
        result.setItem(0, itemStack)
        menu.setRemoteSlot(0, itemStack)
        serverPlayer.connection.send(
            ClientboundContainerSetSlotPacket(
                menu.containerId,
                menu.incrementStateId(),
                0,
                itemStack
            )
        )
    }

    override fun slotsChanged(container: Container) {
        access.execute { level: Level, _: BlockPos? ->
            slotChangedCraftingGrid(
                this,
                level,
                player,
                craftSlots,
                resultSlots
            )
        }
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(access, player, EmpyreanBlocks.ADVANCED_CRAFTING_TABLE)
    }
}