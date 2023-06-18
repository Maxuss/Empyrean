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
    EMPYREAN_L_NAUTICAL(EmpyreanColors.NAUTICAL, EmpyreanColors.NAUTICAL, true) // Empyrean Lerping color Nautical
    ;

    fun merge(with: SpecialFormatting): SpecialFormatting {
        return if(with == NONE) this else with
    }

    companion object {
        @Suppress("KotlinConstantConditions")
        fun fromColor(color: EmpyreanColor): SpecialFormatting {
            if(color is TextColor) {
                return NONE
            } else if(color is LerpingColor) {
                return valueOf("EMPYREAN_L_${color.name}")
            }
            return NONE
        }
    }
}