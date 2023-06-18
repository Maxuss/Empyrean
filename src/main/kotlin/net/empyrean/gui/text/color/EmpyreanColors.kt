package net.empyrean.gui.text.color

import com.google.common.collect.ImmutableList

object EmpyreanColors {
    val colors: HashMap<String, EmpyreanColor> = hashMapOf()

    @JvmField
    val NAUTICAL = addColor("NAUTICAL", LerpingColor("NAUTICAL", ImmutableList.of(
        0x7874fc,
        0x74bcfc,
        0x74fce8
    ), 60f))

    @Suppress("SameParameterValue")
    private fun <T: EmpyreanColor> addColor(name: String, color: T): T {
        colors[name] = color
        return color
    }

    fun findColor(name: String): EmpyreanColor {
        return colors[name]!!
    }
}