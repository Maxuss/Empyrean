package net.empyrean.commands

import net.empyrean.EmpyreanMod
import net.empyrean.chat.SpecialFormatting
import net.empyrean.chat.withAnimation
import net.empyrean.chat.withEmpyreanStyle
import net.empyrean.client.text.animation.TextAnimation
import net.empyrean.commands.api.command
import net.empyrean.commands.api.suggests
import net.empyrean.components.data
import net.empyrean.game.GameManager
import net.empyrean.game.state.ProgressionState
import net.empyrean.item.EmpyreanItemStack
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.clientbound.ClientboundStatusMessagePacket
import net.empyrean.player.PlayerStat
import net.empyrean.registry.EmpyreanRegistries
import net.empyrean.util.pos.pos
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
                Component.literal("This is going to be a horrible night...").withStyle(Style.EMPTY.withColor(0xc91c33)).withAnimation(TextAnimation.SHAKE), 2
            ))
    }
    "stats" runs {
        source.playerOrException.data.rawStats[PlayerStat.DEFENSE] += 10f
    }
    "modStat" {
        argString("stat") { stat ->
            argFloat("value") runs { value ->
                source.playerOrException.data.rawStats[PlayerStat.valueOf(stat())] = value()
                source.playerOrException.data.sync()
            }
        }
    }
    "mana" {
        argFloat("mana") runs { mana ->
            source.playerOrException.data.currentMana = mana()
            source.playerOrException.data.sync()
        }
    }
    "text" runs {
        source.playerOrException.sendSystemMessage(Component.literal("shaking").withAnimation(TextAnimation.SHAKE))
        source.playerOrException.sendSystemMessage(Component.literal("woooooo").withAnimation(TextAnimation.WAVE))
        source.playerOrException.sendSystemMessage(Component.literal("wobblyyy").withAnimation(TextAnimation.WOBBLE))

    }
}

@Suppress("CAST_NEVER_SUCCEEDS")
fun prefixCommand() = command("prefix") {
    val suggest = suggests { EmpyreanRegistries.PREFIX.keySet().toList() }
    argIdentifier("pfx", suggest) runs { id ->
        val player = source.playerOrException
        (player.mainHandItem as EmpyreanItemStack).setPrefix(EmpyreanRegistries.PREFIX[id()]!!)
    }

    runs {
        val player = source.playerOrException
        (player.mainHandItem as EmpyreanItemStack).setPrefix(EmpyreanRegistries.PREFIX.getRandom(EmpyreanMod.serverRandomSource).get().value())
    }
}

fun oreCommand() = command("ore") {
    argIdentifier("ore") runs { oreId ->
        val player = source.playerOrException
        player.level().getChunkAt(player.position().pos).findBlocks({ BuiltInRegistries.BLOCK.getKey(it.block) == oreId() }) { pos, block ->
            player.sendSystemMessage(Component.literal("$pos $block"))
        }
    }
}

fun gameStateCommand() = command("gameState") {
    val suggestion = suggests("state") { ProgressionState.values().map(ProgressionState::toString) }
    argString("state", suggestion) runs {
        GameManager.gameData.progressionState = ProgressionState.valueOf(it())
    }
}