package net.empyrean.nbt.encode

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class NbtEncoder(val out: AppendingCompoundWriter): AbstractEncoder(), CompositeEncoder {
    override val serializersModule: SerializersModule = EmptySerializersModule()
    private var elementName: String? = null

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        return when(descriptor.kind as StructureKind) {
            StructureKind.CLASS, StructureKind.OBJECT, StructureKind.MAP -> {
                elementName = descriptor.getElementName(index)
                true
            }
            else -> true
        }
    }

    private fun beginEncodingValue(value: Any) {
        out.beginEntry(elementName ?: "_value")
        out.writeEntryValue(value)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        when(descriptor.kind) {
            StructureKind.CLASS, StructureKind.OBJECT, StructureKind.MAP -> {
                if(elementName == null) // we are at the beginning probably
                    return this
                out.beginEntry(elementName!!)
                val ref = AppendingCompoundWriter()
                out.writeEntryValue(ref)
                return NbtEncoder(ref)
            }
            StructureKind.LIST -> {
                if(elementName == null) // we are at the beginning probably?
                    return this
                out.beginEntry(elementName!!)
                val ref = AppendingListWriter()
                out.writeEntryValue(ref)
                return NbtListEncoder(ref)
            }
            else -> { /* no-op */ }
        }
        return this
    }

    override fun encodeByte(value: Byte) {
        this.beginEncodingValue(value)
    }

    override fun encodeBoolean(value: Boolean) {
        this.beginEncodingValue(value)
    }

    override fun encodeShort(value: Short) {
        this.beginEncodingValue(value)
    }

    override fun encodeChar(value: Char) {
        this.beginEncodingValue(value.toString())
    }

    override fun encodeDouble(value: Double) {
        this.beginEncodingValue(value)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        this.beginEncodingValue(index)
    }

    override fun encodeFloat(value: Float) {
        this.beginEncodingValue(value)
    }

    override fun encodeValue(value: Any) {
        if(value is UUID) {
            this.beginEncodingValue(value)
        } else {
            return
        }
    }

    override fun encodeInt(value: Int) {
        this.beginEncodingValue(value)
    }

    override fun encodeLong(value: Long) {
        this.beginEncodingValue(value)
    }

    override fun encodeNull() {
        return
    }

    override fun encodeString(value: String) {
        beginEncodingValue(value)
    }
}

@OptIn(ExperimentalSerializationApi::class)
class NbtListEncoder(private val writer: AppendingListWriter): AbstractEncoder() {
    override val serializersModule: SerializersModule
        get() = EmptySerializersModule()

    override fun encodeValue(value: Any) {
        this.writer.writeValue(value)
    }
}

enum class StructureType {
    LIST,
    COMPOUND
}