package net.empyrean.nbt.encode

import net.empyrean.nbt.NbtUtil
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag

class AppendingCompoundWriter {
    private val entries: ArrayDeque<CompoundEntry> = ArrayDeque()
    private var closed: Boolean = false

    fun beginEntry(name: String) {
        entries.add(CompoundEntry(name, null))
    }

    fun writeEntryValue(value: Any) {
        entries.last().value = value
    }

    fun lastList(): AppendingListWriter {
        return entries.last().value as AppendingListWriter
    }

    fun lastCompound(): AppendingCompoundWriter {
        return entries.lastOrNull { val value = it.value; value != null && value is AppendingCompoundWriter && !value.closed }?.value as? AppendingCompoundWriter ?: this
    }

    fun closeLastCompound() {
        val last = entries.lastOrNull { val value = it.value; value != null && value is AppendingCompoundWriter && !value.closed }?.value as? AppendingCompoundWriter ?: return
        last.closed = true
    }

    fun finish(): CompoundTag {
        val cmp = CompoundTag()
        for(entry in entries) {
            val tag = when(val entryValue = entry.value) {
                is AppendingCompoundWriter -> entryValue.finish()
                is AppendingListWriter -> {
                    cmp.put(entry.key, entryValue.finish())
                    continue
                }
                else -> NbtUtil.transformToNbt(entryValue ?: continue)
            } ?: continue
            cmp.put(entry.key, tag)
        }
        return cmp
    }

    private data class CompoundEntry(val key: String, var value: Any?)

    private data class CompoundValue(val valueType: CompoundValueType, var value: Any?)

    @JvmInline
    private value class StagingCompoundValue(val inner: AppendingCompoundWriter)
}

class AppendingListWriter {
    private val entries: ArrayDeque<Any> = ArrayDeque()

    fun writeValue(value: Any) {
        entries.add(value)
    }

    fun finish(): ListTag {
        val tags = entries.mapNotNull(NbtUtil::transformToNbt)
        val listTag = ListTag()
        listTag.addAll(tags)
        return listTag
    }
}

enum class CompoundValueType {
    COMPOUND,
    LIST,
    PRIMITIVE
}