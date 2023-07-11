package net.empyrean.client.text

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import net.empyrean.client.text.animation.EmpyreanTextAnimation
import net.empyrean.client.text.animation.TextAnimation
import net.empyrean.client.text.color.EmpyreanColors
import net.empyrean.client.text.color.LerpingColor

@Serializable(with = ComponentAttachment.Companion::class)
data class ComponentAttachment(
    var specialColor: LerpingColor? = null,
    var specialAnimation: EmpyreanTextAnimation? = null,
) {

    companion object: KSerializer<ComponentAttachment> {
        override val descriptor: SerialDescriptor
            get() = buildClassSerialDescriptor("ComponentAttachment") {
                element<String>("color")
                element<TextAnimation?>("animation")
            }

        @OptIn(ExperimentalSerializationApi::class)
        override fun deserialize(decoder: Decoder): ComponentAttachment =decoder.decodeStructure(descriptor) {
                val color = decodeStringElement(descriptor, 0)
                val animation = decodeNullableSerializableElement(descriptor, 1, TextAnimation.serializer())
                val clr = if(color == "null") null else EmpyreanColors.findColor(color) as? LerpingColor
                return@decodeStructure ComponentAttachment(clr, animation)
            }

        @OptIn(ExperimentalSerializationApi::class)
        override fun serialize(encoder: Encoder, value: ComponentAttachment) {
            encoder.encodeStructure(descriptor) {
                encodeStringElement(descriptor, 0, if(value.specialColor == null) "null" else value.specialColor!!.serialize().substring(4))
                encodeNullableSerializableElement(descriptor, 1, TextAnimation.serializer(), value.specialAnimation as? TextAnimation)
            }
        }
    }
}