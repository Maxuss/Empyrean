package net.empyrean.datagen.ore

import net.empyrean.game.state.ProgressionState
import net.empyrean.predicate.GameStateTest
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

object EmpyreanOreRules {
    val DEEPSLATE_RULE = TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
    val POST_BOSS_1 = GameStateTest(ProgressionState.POST_BOSS_1)
}