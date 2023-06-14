package net.empyrean.render

import net.minecraft.client.gui.font.FontSet
import net.minecraft.resources.ResourceLocation

interface IFontAccessor {
    fun fontSet(rl: ResourceLocation): FontSet
}