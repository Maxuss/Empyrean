package net.empyrean.gui.text

import net.empyrean.chat.SpecialFormatting
import net.empyrean.chat.withEmpyreanStyle
import net.minecraft.network.chat.Component

object Splashes {
    @JvmStatic
    val allSplashes = listOf(
        "Previously known as Macrocosm!",
        "Absolutely Macrocosmic!",
        Component.literal("Even more colors!").withEmpyreanStyle(SpecialFormatting.EMPYREAN_L_NAUTICAL),
        "Empyrical!",
        "uwu",
        "owo",
        "Coming soon!"
    )
}