package net.empyrean.nbt

import net.minecraft.nbt.*
import java.util.*

object NbtUtil {
    fun CompoundTag.getAnyList(key: String): List<Tag> {
        return (get(key) as ListTag).toList()
    }

    fun Tag.inner(): Any {
        return when(this) {
            is NumericTag -> asNumber
            is StringTag -> asString
            is ListTag -> map { inner() }
            is CompoundTag -> this
            else -> "null"
        }
    }

    fun <T> CompoundTag.putValue(key: String, value: T) {
        when(value) {
            is Byte -> putByte(key, value)
            is Short -> putShort(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is UUID -> putUUID(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is String -> putString(key, value)
            is ByteArray -> putByteArray(key, value)
            is IntArray -> putIntArray(key, value)
            is LongArray -> putLongArray(key, value)
            is Boolean -> putBoolean(key, value)
        }
    }

    fun <T> transformToNbt(tag: T): Tag? {
        return when(tag) {
            is Tag -> tag
            is Boolean -> ByteTag.valueOf(tag)
            is Short -> ShortTag.valueOf(tag)
            is Int -> IntTag.valueOf(tag)
            is Long -> LongTag.valueOf(tag)
            is UUID -> StringTag.valueOf(tag.toString())
            is Float -> FloatTag.valueOf(tag)
            is Double -> DoubleTag.valueOf(tag)
            is String -> StringTag.valueOf(tag)
            is List<*> -> {
                val listTag = ListTag()
                listTag.addAll(tag.map { each -> transformToNbt(each ?: return@map null) }.filterNotNull())
                listTag
            }
            else -> null
        }
    }
}