package net.empyrean.chat

import net.empyrean.gui.text.color.EmpyreanColor
import net.empyrean.gui.text.color.EmpyreanColors
import net.empyrean.gui.text.color.LerpingColor
import net.minecraft.network.chat.TextColor

enum class SpecialFormatting(
    val bloomColor: EmpyreanColor? = null,
    val selfColor: EmpyreanColor? = null,
    val drawOutlined: Boolean = false
) {
    NONE,
    EMPYREAN_L_STARLIKE(EmpyreanColors.STARLIKE, EmpyreanColors.STARLIKE, true), // Empyrean Lerping color Nautical
    EMPYREAN_L_EXPERT(EmpyreanColors.EXPERT, EmpyreanColors.EXPERT, false)
    ;

    fun merge(with: SpecialFormatting): SpecialFormatting {
        return if (with == NONE) this else with
    }

    companion object {
        fun fromColor(color: EmpyreanColor): SpecialFormatting {
            if (color is LerpingColor) {
                return valueOf("EMPYREAN_L_${color.lerpName}")
            } else if (color is TextColor) {
                return NONE
            }
            return NONE
        }
    }
}