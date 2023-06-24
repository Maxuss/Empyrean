package net.empyrean

import net.empyrean.datagen.EmpyreanBlockLootGenerator
import net.empyrean.datagen.EmpyreanBlockTagGenerator
import net.empyrean.datagen.EmpyreanItemTagGenerator
import net.empyrean.datagen.EmpyreanModelGenerator
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

object EmpyreanModDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider { generator -> EmpyreanModelGenerator(generator as FabricDataOutput) }
        pack.addProvider { generator -> EmpyreanBlockLootGenerator(generator as FabricDataOutput) }
        pack.addProvider { generator, backingRegistry -> EmpyreanBlockTagGenerator(generator as FabricDataOutput, backingRegistry) }
        pack.addProvider { generator, backingRegistry -> EmpyreanItemTagGenerator(generator as FabricDataOutput, backingRegistry) }
    }
}