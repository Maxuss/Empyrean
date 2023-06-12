@file:JvmName("Serde")

package net.empyrean.nbt

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import net.empyrean.nbt.de.NbtDecoder
import net.empyrean.nbt.ser.AppendingCompoundWriter
import net.empyrean.nbt.ser.NbtEncoder
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import java.util.UUID

fun <T> encodeNbt(serializer: SerializationStrategy<T>, value: T): CompoundTag {
    val encoder = NbtEncoder(AppendingCompoundWriter())
    encoder.encodeSerializableValue(serializer, value)
    return encoder.out.finish()
}

fun <T> encodeNbtTo(serializer: SerializationStrategy<T>, value: T, out: CompoundTag) {
    val encoder = NbtEncoder(AppendingCompoundWriter())
    encoder.encodeSerializableValue(serializer, value)
    encoder.out.finishInto(out)
}

fun <T> decodeNbt(deserializer: DeserializationStrategy<T>, from: CompoundTag): T {
    val decoder = NbtDecoder(from)
    return decoder.decodeSerializableValue(deserializer)
}

inline fun <reified T> encodeNbt(value: T): CompoundTag = encodeNbt(serializer(), value)
inline fun <reified T> encodeNbtTo(value: T, to: CompoundTag) = encodeNbtTo(serializer(), value, to)

inline fun <reified T> decodeNbt(from: CompoundTag): T = decodeNbt(serializer(), from)

typealias Identifier = @Serializable(with = ResourceLocationSer::class) ResourceLocation
typealias SerId = @Serializable(with = UUIDSer::class) UUID

object ResourceLocationSer: KSerializer<ResourceLocation> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ResourceLocation", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ResourceLocation {
        return ResourceLocation(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: ResourceLocation) {
        encoder.encodeString(value.toString())
    }
}

object UUIDSer: KSerializer<UUID> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}