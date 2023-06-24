package net.empyrean.datagen

import net.empyrean.block.EmpyreanBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture

class EmpyreanBlockTagGenerator(output: FabricDataOutput, backingRegistry: CompletableFuture<HolderLookup.Provider>): FabricTagProvider.BlockTagProvider(output, backingRegistry) {
    override fun addTags(arg: HolderLookup.Provider) {
        // <editor-fold desc="Mineable Pickaxe">
        val pickaxe = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
        pickaxe.add(EmpyreanBlocks.GEYSERITE_ORE)
        // </editor-fold>

        // <editor-fold desc="Requires Tool Levels">
        val diamond = getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
        diamond.add(EmpyreanBlocks.GEYSERITE_ORE)
        // </editor-fold>
    }

}