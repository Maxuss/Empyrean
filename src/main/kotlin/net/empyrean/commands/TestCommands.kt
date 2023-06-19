package net.empyrean.commands

import net.empyrean.chat.SpecialFormatting
import net.empyrean.chat.withEmpyreanStyle
import net.empyrean.commands.api.command
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.clientbound.ClientboundStatusMessagePacket
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
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
    "status" runs {
        EmpyreanNetworking.EMPYREAN_CHANNEL
            .serverHandle(source.player!!)
            .send(ClientboundStatusMessagePacket(
                Component.literal("This is going to be a horrible night...").withStyle(Style.EMPTY.withColor(0xc91c33)), 2
            ))
    }
}