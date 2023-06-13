package net.empyrean.gui.text

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component

object Splashes {
    @JvmStatic
    val allSplashes = listOf<Any>(
        "Previously known as Macrocosm!",
        "Absolutely Macrocosmic!",
        Component.literal("Even more colors!").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD),
        "Empyrical!",
        "uwu",
        "owo",
        "Coming soon!"
    )
}