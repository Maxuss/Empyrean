package net.empyrean.commands

import net.empyrean.chat.SpecialFormatting
import net.empyrean.chat.withEmpyreanStyle
import net.empyrean.commands.api.command
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance

fun testCommand() = command("empyrean") {
    "test" runs {
        source.player?.sendSystemMessage(
            Component.literal("Hello, empyrean world!").withEmpyreanStyle(SpecialFormatting.EMPYREAN_L_STARLIKE)
        )
    }
    "effects" runs {
        BuiltInRegistries.MOB_EFFECT.forEach { effect ->
            source.player?.addEffect(MobEffectInstance(effect, 20 * 10, 2))
        }
    }
}