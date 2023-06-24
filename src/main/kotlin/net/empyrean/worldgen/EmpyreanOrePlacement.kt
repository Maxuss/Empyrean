package net.empyrean.worldgen

import net.empyrean.datagen.ore.EmpyreanOreKeys
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.world.level.levelgen.GenerationStep

fun bootstrapOrePlacement() {
    BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, EmpyreanOreKeys.PLACED_GEYSERITE)
}