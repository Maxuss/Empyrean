package net.empyrean.util.serde

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation

open class RegistrySerializer<V>(val registry: Registry<V>): KSerializer<V?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("${registry.key()}/value", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): V? {
        return try { registry[ResourceLocation(decoder.decodeString())] } catch (e: Exception) { null }
    }

    override fun serialize(encoder: Encoder, value: V?) {
        if(value == null)
            encoder.encodeString("empyrean:null")
        else
            encoder.encodeString(registry.getKey(value)?.toString() ?: "empyrean:null")
    }
}