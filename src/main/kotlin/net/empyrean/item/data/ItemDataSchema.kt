package net.empyrean.item.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import net.empyrean.nbt.NbtUtil.inner
import net.empyrean.nbt.decodeNbt
import net.empyrean.nbt.encodeNbt
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import kotlin.reflect.KProperty

open class ItemDataSchema(internal val bound: ItemStack) {
    val properties = mutableListOf<SchemaProperty<*>>()

    inline fun <reified T> property(name: String, defaultValue: T): SchemaProperty<T> {
        val prop = SchemaProperty(name, serializer<T>(), defaultValue, null)
        properties.add(prop)
        return prop
    }

    fun saveDefault() {
        val outTag = bound.getOrCreateTagElement("empyrean")
        for (prop in properties) {
            prop.saveDefault(outTag)
        }
    }

    fun validate() {
        val inTag = bound.getOrCreateTagElement("empyrean")
        for (prop in properties) {
            if (inTag.contains(prop.name))
                prop.load(inTag)
            else
                prop.saveDefault(inTag)
        }
    }
}

class SchemaProperty<T>(
    internal val name: String,
    private val serializer: KSerializer<T>,
    private val default: T,
    private var currentData: T?
) {
    fun saveDefault(to: CompoundTag) {
        val encoded = encodeNbt(serializer, default)
        if (encoded.contains("_value")) {
            to.put(name, encoded["_value"]!!)
        } else {
            to.put(name, encoded)
        }
    }

    fun encode(to: CompoundTag, value: T) {
        val encoded = encodeNbt(serializer, value)
        if (encoded.contains("_value")) {
            to.put(name, encoded["_value"]!!)
        } else {
            to.put(name, encoded)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun load(from: CompoundTag): T? {
        val tag = from[name] ?: return null
        return if (tag is CompoundTag) {
            decodeNbt(serializer, tag.getCompound(name))
        } else {
            tag.inner() as? T
        }
    }

    operator fun getValue(self: ItemDataSchema, prop: KProperty<*>): T {
        if (currentData != null)
            return currentData!!
        val nullableData = load(self.bound.getOrCreateTagElement("empyrean"))
        currentData = nullableData
        return nullableData ?: default
    }

    operator fun setValue(self: ItemDataSchema, prop: KProperty<*>, value: T) {
        currentData = value
        encode(self.bound.getOrCreateTagElement("empyrean"), value)
    }
}