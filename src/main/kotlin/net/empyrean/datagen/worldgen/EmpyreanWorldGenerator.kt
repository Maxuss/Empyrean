package net.empyrean.datagen.worldgen

import net.empyrean.EmpyreanMod
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import java.util.concurrent.CompletableFuture

@Suppress("UnstableApiUsage")
class EmpyreanWorldGenerator(out: FabricDataOutput, backingRegistry: CompletableFuture<HolderLookup.Provider>): FabricDynamicRegistryProvider(out, backingRegistry) {
    override fun getName(): String {
        return EmpyreanMod.modId
    }

    override fun configure(registries: HolderLookup.Provider, entries: Entries) {
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE))
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE))
    }
}