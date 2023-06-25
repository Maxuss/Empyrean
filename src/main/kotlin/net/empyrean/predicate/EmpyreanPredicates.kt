package net.empyrean.predicate

import com.mojang.serialization.Codec
import net.empyrean.game.GameManager
import net.empyrean.game.state.ProgressionState
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType

class CompoundTest(val inner: List<RuleTest>): RuleTest() {
    override fun test(state: BlockState, random: RandomSource): Boolean {
        return inner.all { it.test(state, random) }
    }

    override fun getType(): RuleTestType<*> {
        return BuiltInRegistries.RULE_TEST[ResourceLocation("empyrean", "compound_test")]!!
    }

    companion object {
        val CODEC: Codec<CompoundTest> = RuleTest.CODEC.listOf().fieldOf("tests").xmap(
            { states -> CompoundTest(states) },
            { test -> test.inner }
        ).codec()
    }
}

class GameStateTest(val progressionState: ProgressionState): RuleTest() {
    override fun test(state: BlockState, random: RandomSource): Boolean {
        return GameManager.gameData.progressionState == progressionState
    }

    override fun getType(): RuleTestType<*> {
        return BuiltInRegistries.RULE_TEST[ResourceLocation("empyrean", "game_state")]!!
    }

    companion object {
        val CODEC: Codec<GameStateTest> = (StringRepresentable.fromEnum { ProgressionState.values() }.fieldOf("state")).xmap(
            { progressionState -> GameStateTest(progressionState) },
            { test -> test.progressionState }).codec()
    }
}