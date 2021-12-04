package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.kyori.adventure.sound.Sound
import net.minestom.server.potion.Potion
import net.minestom.server.potion.PotionEffect
import kotlin.experimental.and

@Serializer(forClass = Sound::class)
@OptIn(ExperimentalSerializationApi::class)
object PotionSerializer : KSerializer<Potion> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Sound") {
            element<PotionEffect>("effect")
            element<Byte>("amplifier")
            element<Int>("duration")
            element<Byte>("flags")
        }


    override fun serialize(encoder: Encoder, value: Potion) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, PotionEffectSerializer, value.effect)
            encodeByteElement(descriptor, 1, value.amplifier)
            encodeIntElement(descriptor, 2, value.duration)
            encodeByteElement(descriptor, 3, value.flags)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Potion =
        decoder.decodeStructure(descriptor) {
            var potionEffect: PotionEffect? = null
            var amplifier = 0.toByte()
            var duration = 0
            var flags = 0.toByte()

            if(decodeSequentially()) {
                potionEffect = decodeSerializableElement(descriptor, 0, PotionEffectSerializer)
                amplifier = decodeByteElement(descriptor, 1)
                duration = decodeIntElement(descriptor, 2)
                flags = decodeByteElement(descriptor, 3)
            } else while (true) {
                when(val index = decodeElementIndex((descriptor))) {
                    0 -> potionEffect = decodeSerializableElement(descriptor, 0, PotionEffectSerializer)
                    1 -> amplifier = decodeByteElement(descriptor, 1)
                    2 -> duration = decodeIntElement(descriptor, 2)
                    3 -> flags = decodeByteElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            Potion(
                potionEffect!!, amplifier, duration,
                flags
            )
        }

}