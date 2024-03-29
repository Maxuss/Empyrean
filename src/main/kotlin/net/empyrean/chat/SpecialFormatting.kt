package net.empyrean.chat

import net.empyrean.client.text.color.EmpyreanColor
import net.empyrean.client.text.color.EmpyreanColors
import net.empyrean.client.text.color.LerpingColor
import net.minecraft.network.chat.TextColor

enum class SpecialFormatting(
    val bloomColor: EmpyreanColor? = null,
    val selfColor: EmpyreanColor? = null,
    val drawOutlined: Boolean = false
) {
    NONE,
    EMPYREAN_L_STARLIKE(EmpyreanColors.STARLIKE, EmpyreanColors.STARLIKE, true), // Empyrean Lerping color Nautical
    EMPYREAN_L_EXPERT(EmpyreanColors.EXPERT, EmpyreanColors.EXPERT, false),
    EMPYREAN_L_CINDER(EmpyreanColors.CINDER, EmpyreanColors.CINDER, true),
    EMPYREAN_L_ADRENALINE(EmpyreanColors.ADRENALINE, EmpyreanColors.ADRENALINE, false),

    EMPYREAN_L_MELEE_ABILITY(EmpyreanColors.MELEE_ABILITY, EmpyreanColors.MELEE_ABILITY, false),
    EMPYREAN_L_RANGED_ABILITY(EmpyreanColors.RANGED_ABILITY, EmpyreanColors.RANGED_ABILITY, false),
    EMPYREAN_L_MAGE_ABILITY(EmpyreanColors.MAGE_ABILITY, EmpyreanColors.MAGE_ABILITY, false),
    EMPYREAN_L_ROGUE_ABILITY(EmpyreanColors.ROGUE_ABILITY, EmpyreanColors.ROGUE_ABILITY, false),
    EMPYREAN_L_SUMMONER_ABILITY(EmpyreanColors.SUMMONER_ABILITY, EmpyreanColors.SUMMONER_ABILITY, false)
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