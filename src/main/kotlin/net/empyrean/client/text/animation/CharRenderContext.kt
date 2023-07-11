package net.empyrean.client.text.animation

data class CharRenderContext(
    val index: Int,
    val dropShadow: Boolean,
    var xOffset: Float,
    var yOffset: Float
) {
}