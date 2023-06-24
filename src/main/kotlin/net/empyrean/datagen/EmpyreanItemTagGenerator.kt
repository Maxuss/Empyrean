package net.empyrean.datagen

import net.empyrean.registry.EmpyreanItems
import net.empyrean.tag.EmpyreanTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class EmpyreanItemTagGenerator(output: FabricDataOutput, backingRegistry: CompletableFuture<HolderLookup.Provider>): FabricTagProvider.ItemTagProvider(output, backingRegistry) {
    override fun addTags(arg: HolderLookup.Provider) {
        // <editor-fold desc="Empyrean">
        val volatile = getOrCreateTagBuilder(EmpyreanTags.VOLATILE)
        volatile.add(EmpyreanItems.RAW_GEYSERITE)
        // </editor-fold>
    }
}