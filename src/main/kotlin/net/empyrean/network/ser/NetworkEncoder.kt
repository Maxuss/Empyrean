package net.empyrean.network.ser

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.network.FriendlyByteBuf

@OptIn(ExperimentalSerializationApi::class)
class NetworkEncoder(val out: FriendlyByteBuf): AbstractEncoder() {
    override val serializersModule: SerializersModule
        get() = EmptySerializersModule()

    override fun encodeBoolean(value: Boolean) {
        out.writeBoolean(value)
    }

    override fun encodeByte(value: Byte) {
        out.writeByte(value.toInt())
    }

    override fun encodeChar(value: Char) {
        out.writeChar(value.code)
    }

    override fun encodeDouble(value: Double) {
        out.writeDouble(value)
    }

    override fun encodeNull() {
        out.writeByte(0)
    }

    override fun encodeInt(value: Int) {
        out.writeVarInt(value)
    }

    override fun encodeLong(value: Long) {
        out.writeVarLong(value)
    }

    override fun encodeFloat(value: Float) {
        out.writeFloat(value)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        out.writeInt(index)
    }

    override fun encodeShort(value: Short) {
        out.writeShort(value.toInt())
    }

    override fun encodeString(value: String) {
        out.writeVarInt(value.length)
        out.writeBytes(value.encodeToByteArray())
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        out.writeVarInt(collectionSize)
        return this
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return when(descriptor.kind) {
            StructureKind.MAP -> {
                out.writeVarInt(descriptor.elementsCount)
                this
            }
            else -> this
        }
    }
}