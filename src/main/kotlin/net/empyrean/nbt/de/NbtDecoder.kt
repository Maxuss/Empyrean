package net.empyrean.nbt.de

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.empyrean.nbt.NbtUtil.getAnyList
import net.empyrean.nbt.NbtUtil.inner
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag

@OptIn(ExperimentalSerializationApi::class)
data class NbtDecoder(private val reader: CompoundTag) : AbstractDecoder() {
    private val keys = reader.allKeys.toList()
    private var nextIdx = 0
    private val idx get() = nextIdx - 1
    private val key get() = keys[idx]

    override val serializersModule: SerializersModule
        get() = EmptySerializersModule()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (nextIdx == descriptor.elementsCount)
            return CompositeDecoder.DECODE_DONE
        return try {
            descriptor.getElementIndex(keys[nextIdx++])
        } catch (e: Exception) {
            CompositeDecoder.DECODE_DONE
        }
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        return if (descriptor.kind == StructureKind.LIST) {
            DecodeList(this, reader.getAnyList(keys[idx]))
        } else {
            if (idx == -1) this else NbtDecoder(reader.getCompound(keys[idx]))
        }
    }

    override fun decodeBoolean(): Boolean {
        return reader.getBoolean(key)
    }

    override fun decodeByte(): Byte {
        return reader.getByte(key)
    }

    override fun decodeChar(): Char {
        return reader.getString(key)[0]
    }

    override fun decodeDouble(): Double {
        return reader.getDouble(key)
    }

    override fun decodeFloat(): Float {
        return reader.getFloat(key)
    }

    override fun decodeLong(): Long {
        return reader.getLong(key)
    }

    override fun decodeInt(): Int {
        return reader.getInt(key)
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return reader.getInt(key)
    }

    override fun decodeShort(): Short {
        return reader.getShort(key)
    }

    override fun decodeString(): String {
        return reader.getString(key)
    }
}

@OptIn(ExperimentalSerializationApi::class)
class DecodeList(private val parent: NbtDecoder, private val list: List<Tag>) : AbstractDecoder() {
    private var idx = 0

    override val serializersModule: SerializersModule
        get() = EmptySerializersModule()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (idx + 1 > list.size)
            return CompositeDecoder.DECODE_DONE
        return idx
    }

    override fun decodeBoolean(): Boolean {
        val value = list[idx]
        idx += 1
        return (value.inner() as Number) == 0x01
    }

    override fun decodeValue(): Any {
        val value = list[idx]
        idx += 1
        return value.inner()
    }
}