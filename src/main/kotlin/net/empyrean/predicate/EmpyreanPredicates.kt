package net.empyrean.predicate

import com.mojang.serialization.Codec
import net.empyrean.game.GameManager
import net.empyrean.game.state.ProgressionState
import net.minecraft.util.RandomSource
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType

object EmpyreanPredicates {
    val GAME_STATE_PREDICATE = RuleTestType.register("empyrean:game_state", GameStateTest.CODEC)
    val COMPOUND_PREDICATE = RuleTestType.register("empyrean:compound_test", CompoundTest.CODEC)
}

class CompoundTest(val inner: List<RuleTest>): RuleTest() {
    override fun test(state: BlockState, random: RandomSource): Boolean {
        return inner.all { it.test(state, random) }
    }

    override fun getType(): RuleTestType<*> {
        return EmpyreanPredicates.COMPOUND_PREDICATE
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
        return EmpyreanPredicates.GAME_STATE_PREDICATE
    }

    companion object {
        val CODEC: Codec<GameStateTest> = (StringRepresentable.fromEnum { ProgressionState.values() }.fieldOf("state")).xmap(
            { progressionState -> GameStateTest(progressionState) },
            { test -> test.progressionState }).codec()
    }
}