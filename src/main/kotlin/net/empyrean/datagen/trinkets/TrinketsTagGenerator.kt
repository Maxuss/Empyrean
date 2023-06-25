package net.empyrean.datagen.trinkets

import net.empyrean.registry.EmpyreanItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import java.util.concurrent.CompletableFuture

class TrinketsTagGenerator(out: FabricDataOutput, backingRegistry: CompletableFuture<HolderLookup.Provider>): FabricTagProvider.ItemTagProvider(out, backingRegistry) {
    companion object {
        private val CHEST_WINGS: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("trinkets", "chest/wings"))
    }

    override fun addTags(arg: HolderLookup.Provider) {
        // <editor-fold desc="Wings">
        val wings = getOrCreateTagBuilder(CHEST_WINGS)
        wings.add(EmpyreanItems.ANGEL_WINGS)
        wings.add(EmpyreanItems.FLEDGLING_WINGS)
        wings.add(EmpyreanItems.INSANE_WINGS)
        // </editor-fold>
    }

    override fun getName(): String {
        return "TrinketTags"
    }
}