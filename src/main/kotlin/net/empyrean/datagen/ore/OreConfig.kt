package net.empyrean.datagen.ore

data class OreConfig(
    var veinSize: Int,
    var perChunk: Int,
    var bottom: Int,
    var top: Int,
    var discardChance: Float,
    var offset: Boolean,
    var trapezoid: Boolean
)