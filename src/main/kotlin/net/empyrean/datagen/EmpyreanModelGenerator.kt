package net.empyrean.datagen

import net.empyrean.block.EmpyreanBlocks
import net.empyrean.registry.EmpyreanItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates

class EmpyreanModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blocks: BlockModelGenerators) {
        blocks.createTrivialCube(EmpyreanBlocks.GEYSERITE_ORE)
    }

    override fun generateItemModels(items: ItemModelGenerators) {
        items.generateFlatItem(EmpyreanItems.ASPECT_OF_THE_END, ModelTemplates.FLAT_HANDHELD_ITEM)
        items.generateFlatItem(EmpyreanItems.COSMILIUM_INGOT, ModelTemplates.FLAT_ITEM)
        items.generateFlatItem(EmpyreanItems.FLEDGLING_WINGS, ModelTemplates.FLAT_ITEM)
        items.generateFlatItem(EmpyreanItems.ANGEL_WINGS, ModelTemplates.FLAT_ITEM)
        items.generateFlatItem(EmpyreanItems.PRECURSOR_ASHES, ModelTemplates.FLAT_ITEM)
        items.generateFlatItem(EmpyreanItems.RAW_GEYSERITE, ModelTemplates.FLAT_ITEM)
    }
}