package net.empyrean.network

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import net.empyrean.network.de.NetworkDecoder
import net.empyrean.network.ser.NetworkEncoder
import net.minecraft.network.FriendlyByteBuf

inline fun <reified T> encodePacket(packet: T, out: FriendlyByteBuf) = encodePacket(serializer(), packet, out)

fun <T> encodePacket(serializer: SerializationStrategy<T>, packet: T, out: FriendlyByteBuf) = NetworkEncoder(out).encodeSerializableValue(serializer, packet)

inline fun <reified T> decodePacket(from: FriendlyByteBuf): T = decodePacket(serializer(), from)

fun <T> decodePacket(serializer: DeserializationStrategy<T>, from: FriendlyByteBuf) = NetworkDecoder(from).decodeSerializableValue(serializer)