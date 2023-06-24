package net.empyrean.game.state

import net.minecraft.util.StringRepresentable

enum class ProgressionState: StringRepresentable {
    PRE_BOSS,
    POST_BOSS_1

    ;

    override fun getSerializedName(): String {
        return name
    }
}