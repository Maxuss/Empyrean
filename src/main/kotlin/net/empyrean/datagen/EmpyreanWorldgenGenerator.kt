package net.empyrean.datagen

import net.empyrean.block.EmpyreanBlocks
import net.empyrean.datagen.ore.EmpyreanOreConfigs
import net.empyrean.datagen.ore.EmpyreanOreKeys
import net.empyrean.datagen.ore.EmpyreanOreRules
import net.empyrean.datagen.ore.OreConfig
import net.empyrean.predicate.CompoundTest
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest


object EmpyreanWorldgenGenerator {
    fun initConfiguredFeatures(registry: BootstapContext<ConfiguredFeature<*, *>>) {
        configuredFeature(registry, EmpyreanOreKeys.GEYSERITE_ORE, CompoundTest(listOf(EmpyreanOreRules.DEEPSLATE_RULE, EmpyreanOreRules.POST_BOSS_1)), EmpyreanBlocks.GEYSERITE_ORE, EmpyreanOreConfigs.GEYSERITE)
    }

    fun initPlacedFeatures(registry: BootstapContext<PlacedFeature>) {
        val featureLookup = registry.lookup(Registries.CONFIGURED_FEATURE)
        placedFeature(registry, featureLookup.getOrThrow(EmpyreanOreKeys.GEYSERITE_ORE).key(), EmpyreanOreKeys.PLACED_GEYSERITE, EmpyreanOreConfigs.GEYSERITE)
    }

    private fun placedFeature(registry: BootstapContext<PlacedFeature>, cfgKey: ResourceKey<ConfiguredFeature<*, *>>, placedKey: ResourceKey<PlacedFeature>, config: OreConfig) {
        if(config.offset) {
            placedOffset(registry, cfgKey, placedKey, config)
        } else if(config.trapezoid) {
            placedTrapezoid(registry, cfgKey, placedKey, config)
        } else {
            placedUniform(registry, cfgKey, placedKey, config)
        }
    }

    fun placedUniform(
        registry: BootstapContext<PlacedFeature>,
        cfgKey: ResourceKey<ConfiguredFeature<*, *>>,
        placedKey: ResourceKey<PlacedFeature>,
        config: OreConfig
    ) {
        val featureLookup = registry.lookup(Registries.CONFIGURED_FEATURE)
        PlacementUtils.register(registry, placedKey, featureLookup.getOrThrow(cfgKey), modifiersWithCount(config.perChunk, HeightRangePlacement.uniform(
            VerticalAnchor.absolute(config.bottom), VerticalAnchor.absolute(config.top))))
    }

    fun placedTrapezoid(
        registry: BootstapContext<PlacedFeature>,
        cfgKey: ResourceKey<ConfiguredFeature<*, *>>,
        placedKey: ResourceKey<PlacedFeature>,
        config: OreConfig
    ) {
        val featureLookup = registry.lookup(Registries.CONFIGURED_FEATURE)
        PlacementUtils.register(
            registry,
            placedKey,
            featureLookup.getOrThrow(cfgKey),
            modifiersWithCount(
                config.perChunk,
                HeightRangePlacement.triangle(VerticalAnchor.absolute(config.bottom), VerticalAnchor.absolute(config.top))
            )
        )
    }

    private fun placedOffset(registry: BootstapContext<PlacedFeature>, cfgKey: ResourceKey<ConfiguredFeature<*, *>>, placedKey: ResourceKey<PlacedFeature>, config: OreConfig) {
        val featureLookup = registry.lookup(Registries.CONFIGURED_FEATURE)
        PlacementUtils.register(registry, placedKey, featureLookup.getOrThrow(cfgKey), modifiersWithCount(config.perChunk, HeightRangePlacement.uniform(
            VerticalAnchor.aboveBottom(config.bottom), VerticalAnchor.absolute(config.top))))
    }

    private fun modifiers(
        countModifier: PlacementModifier,
        heightModifier: PlacementModifier
    ): List<PlacementModifier> {
        return listOf(
            countModifier,
            InSquarePlacement.spread(),
            heightModifier,
            BiomeFilter.biome()
        )
    }

    private fun modifiersWithCount(count: Int, heightModifier: PlacementModifier): List<PlacementModifier> {
        return modifiers(CountPlacement.of(count), heightModifier)
    }

    private fun configuredFeature(registry: BootstapContext<ConfiguredFeature<*, *>>, key: ResourceKey<ConfiguredFeature<*, *>>, rule: RuleTest, oreBlock: Block, config: OreConfig) {
        FeatureUtils.register(registry, key, Feature.ORE, OreConfiguration(rule, oreBlock.defaultBlockState(), config.veinSize, config.discardChance))
    }
}