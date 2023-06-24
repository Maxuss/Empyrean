package net.empyrean.datagen

import net.empyrean.block.EmpyreanBlocks
import net.empyrean.registry.EmpyreanItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.world.level.block.Block

class EmpyreanBlockLootGenerator(output: FabricDataOutput): FabricBlockLootTableProvider(output) {
    override fun generate() {
        this.add(
            EmpyreanBlocks.GEYSERITE_ORE
        ) { block: Block ->
            createOreDrop(
                block,
                EmpyreanItems.RAW_GEYSERITE
            )
        }
    }
}