package net.empyrean.item.client

import dev.emi.trinkets.api.client.TrinketRenderer
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.empyrean.item.impl.trinket.DelegatedTrinketRenderer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item

fun bootstrapClientItems() {
    for(item in BuiltInRegistries.ITEM.filter { it is DelegatedTrinketRenderer }) {
        val rendererClass = Class.forName((item as DelegatedTrinketRenderer).rendererClassName)
        val renderer = rendererClass.getConstructor(Item::class.java).newInstance(item) as TrinketRenderer
        TrinketRendererRegistry.registerRenderer(item, renderer)
    }
}