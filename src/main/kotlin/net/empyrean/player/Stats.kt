package net.empyrean.player

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.*
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextColor
import java.util.*

val ChatFormatting.modern: TextColor get() = TextColor.fromLegacyFormat(this)!!

@Serializable
enum class PlayerStat(
    val defaultValue: Float,
    val color: TextColor
) {
    HEALTH(100f, ChatFormatting.RED.modern),
    INTELLIGENCE(100f, ChatFormatting.AQUA.modern),
    DEFENSE(0f, ChatFormatting.GREEN.modern),
    STRENGTH(0f, ChatFormatting.RED.modern),
    BASE_DAMAGE(0f, ChatFormatting.RED.modern),

    ;
}

@JvmInline @Serializable(with = StatsSerializer::class)
value class Stats(val inner: EnumMap<PlayerStat, Float>) {
    companion object {
        fun empty() = Stats(EnumMap(PlayerStat.values().associateWith { it.defaultValue }))
    }

    var health: Float
        get() = inner.getOrPut(PlayerStat.HEALTH) { PlayerStat.HEALTH.defaultValue }
        set(v) { inner[PlayerStat.HEALTH] = v }

    var intel: Float
        get() = inner.getOrPut(PlayerStat.INTELLIGENCE) { PlayerStat.INTELLIGENCE.defaultValue }
        set(v) { inner[PlayerStat.INTELLIGENCE] = v }

    var defense: Float
        get() = inner.getOrPut(PlayerStat.DEFENSE) { PlayerStat.DEFENSE.defaultValue }
        set(v) { inner[PlayerStat.DEFENSE] = v }

    var strength: Float
        get() = inner.getOrPut(PlayerStat.STRENGTH) { PlayerStat.STRENGTH.defaultValue }
        set(v) { inner[PlayerStat.STRENGTH] = v }

    var damage: Float
        get() = inner.getOrPut(PlayerStat.BASE_DAMAGE) { PlayerStat.BASE_DAMAGE.defaultValue }
        set(v) { inner[PlayerStat.BASE_DAMAGE] = v }
}

@OptIn(ExperimentalSerializationApi::class)
object StatsSerializer: KSerializer<Stats> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("net.empyrean.player.Stats") {
            PlayerStat.values().forEach {
                element(it.name.lowercase(), serialDescriptor<Float>().nullable)
            }
        }

    override fun deserialize(decoder: Decoder): Stats {
        val outMap = EnumMap<PlayerStat, Float>(PlayerStat::class.java)
        decoder.decodeStructure(descriptor) {
            val expectedSize = PlayerStat.values().size
            for(single in 0 until expectedSize) {
                val idx = decodeElementIndex(descriptor)
                if(idx == CompositeDecoder.DECODE_DONE)
                    break
                val stat = PlayerStat.values()[idx]
                val value = decodeFloatElement(descriptor, idx)
                outMap[stat] = value
            }
            PlayerStat.values().forEach {
                outMap.putIfAbsent(it, it.defaultValue)
            }
        }
        return Stats(outMap)
    }

    override fun serialize(encoder: Encoder, value: Stats) {
        encoder.encodeStructure(descriptor) {
            value.inner.toList().forEachIndexed { idx, (_, value) ->
                encodeFloatElement(descriptor, idx, value)
            }
        }
    }
}