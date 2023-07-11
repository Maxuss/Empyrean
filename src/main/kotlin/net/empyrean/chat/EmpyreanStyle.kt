package net.empyrean.chat

import net.empyrean.client.text.color.EmpyreanColor
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style

interface EmpyreanStyle {
    fun withSpecial(fmt: SpecialFormatting): Style
    val specialFormat: SpecialFormatting
}

fun MutableComponent.withEmpyreanStyle(spec: SpecialFormatting): MutableComponent =
    this.withStyle { (style as EmpyreanStyle).withSpecial(spec) }

fun MutableComponent.withColor(color: EmpyreanColor): MutableComponent {
    val spec = SpecialFormatting.fromColor(color)
    return if(spec == SpecialFormatting.NONE)
        this.withStyle { it.withColor(color.colorValue) }
    else
        this.withEmpyreanStyle(spec)
}