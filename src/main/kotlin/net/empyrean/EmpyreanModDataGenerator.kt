package net.empyrean

import net.empyrean.datagen.*
import net.empyrean.datagen.trinkets.TrinketsTagGenerator
import net.empyrean.datagen.worldgen.EmpyreanWorldGenerator
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

object EmpyreanModDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider { generator -> EmpyreanModelGenerator(generator as FabricDataOutput) }
        pack.addProvider { generator -> EmpyreanBlockLootGenerator(generator as FabricDataOutput) }
        pack.addProvider { generator, backingRegistry -> EmpyreanBlockTagGenerator(generator as FabricDataOutput, backingRegistry) }
        pack.addProvider { generator, backingRegistry -> EmpyreanItemTagGenerator(generator as FabricDataOutput, backingRegistry) }
        pack.addProvider { generator, backingRegistry -> EmpyreanWorldGenerator(generator as FabricDataOutput, backingRegistry) }
        pack.addProvider { generator, backingRegistry -> TrinketsTagGenerator(generator as FabricDataOutput, backingRegistry) }
    }

    override fun buildRegistry(registryBuilder: RegistrySetBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, EmpyreanWorldgenGenerator::initConfiguredFeatures)
        registryBuilder.add(Registries.PLACED_FEATURE, EmpyreanWorldgenGenerator::initPlacedFeatures)
    }
}