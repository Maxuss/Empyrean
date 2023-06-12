package net.empyrean.network.de

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.network.FriendlyByteBuf

@OptIn(ExperimentalSerializationApi::class)
class NetworkDecoder(private val reader: FriendlyByteBuf): AbstractDecoder() {
    private var idx = 0

    override val serializersModule: SerializersModule
        get() = EmptySerializersModule()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if(idx == descriptor.elementsCount)
            return CompositeDecoder.DECODE_DONE
        return idx++
    }

    override fun decodeBoolean(): Boolean {
        return reader.readBoolean()
    }

    override fun decodeByte(): Byte {
        return reader.readByte()
    }

    override fun decodeChar(): Char {
        return reader.readChar()
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return reader.readVarInt()
    }

    override fun decodeDouble(): Double {
        return reader.readDouble()
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return reader.readInt()
    }

    override fun decodeFloat(): Float {
        return reader.readFloat()
    }

    override fun decodeInt(): Int {
        return reader.readVarInt()
    }

    override fun decodeLong(): Long {
        return reader.readVarLong()
    }

    override fun decodeSequentially(): Boolean {
        return true
    }

    override fun decodeShort(): Short {
        return reader.readShort()
    }

    override fun decodeNull(): Nothing? {
        reader.readerIndex(reader.readerIndex() + 1)
        return null
    }

    override fun decodeString(): String {
        val len = reader.readVarInt()
        return Charsets.UTF_8.decode(reader.readBytes(len).nioBuffer()).toString()
    }
}