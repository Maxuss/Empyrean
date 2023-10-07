package net.empyrean.client.text.color

import com.google.common.collect.ImmutableList

object EmpyreanColors {
    val colors: HashMap<String, EmpyreanColor> = hashMapOf()

    @JvmField
    val STARLIKE = addColor(
        "STARLIKE", LerpingColor(
            "STARLIKE", ImmutableList.of(
                0x7874fc,
                0x74bcfc,
                0x74fce8
            ), 80f
        )
    )

    @JvmField
    val CINDER = addColor(
        "CINDER", LerpingColor(
            "CINDER", ImmutableList.of(
                0xf9531b,
                0xf91b1b,
                0xf9831b,
                0xf2d974,
                0xe85422
            ), 100f
        )
    )

    @JvmField
    val EXPERT = addColor(
        "EXPERT", LerpingColor(
            "EXPERT", ImmutableList.of(
                0xe53737, // red
                0xe5a537, // orange
                0xf4e842, // yellow
                0x9bf442, // green
                0x42f471, // aqua
                0x42f4e8, // bluer aqua
                0x4262f4, // blue
                0xaa42f4, // magenta
                0xf442c5, // pink
            ), 60f
        )
    )

    @JvmField
    val MELEE_ABILITY = addColor(
        "MELEE_ABILITY", LerpingColor(
            "MELEE_ABILITY", ImmutableList.of(
                0xf47b46,
                0xf46346,
                0xf2cb71,
                0xf7a042
            ), 60f
        )
    )

    @JvmField
    val RANGED_ABILITY = addColor(
        "RANGED_ABILITY", LerpingColor(
            "RANGED_ABILITY", ImmutableList.of(
                0xc3ef81,
                0x81ef91,
                0x94fccd,
            ), 60f
        )
    )

    @JvmField
    val SUMMONER_ABILITY = addColor(
        "SUMMONER_ABILITY", LerpingColor(
            "SUMMONER_ABILITY", ImmutableList.of(
                0x94e9fc,
                0x62c6fc,
                0x629dfc,
                0x6274fc
            ), 60f
        )
    )

    @JvmField
    val MAGE_ABILITY = addColor(
        "MAGE_ABILITY", LerpingColor(
            "MAGE_ABILITY", ImmutableList.of(
                0xec90f4,
                0xeebff2,
                0xf9b8ea,
                0xf9b8cf,
                0xf9ccb8
            ), 60f
        )
    )

    @JvmField
    val ROGUE_ABILITY = addColor(
        "ROGUE_ABILITY", LerpingColor(
            "ROGUE_ABILITY", ImmutableList.of(
                0x7f79f7,
                0x6b5afc,
                0x8875f4,
                0x895ffc
            ), 60f
        )
    )

    @JvmField
    val ADRENALINE = addColor(
        "ADRENALINE", LerpingColor(
            "ADRENALINE", ImmutableList.of(
                0x7a3af2,
                0x7114e2,
                0x923ef9
            )
        )
    )

    @Suppress("SameParameterValue")
    private fun <T : EmpyreanColor> addColor(name: String, color: T): T {
        colors[name] = color
        return color
    }

    fun findColor(name: String): EmpyreanColor {
        return colors[name]!!
    }
}