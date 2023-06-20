package net.empyrean.item.impl.trinket

interface DelegatedTrinketRenderer {
    val rendererClassName: String get() = "net.empyrean.render.item.${this.javaClass.simpleName}"
}