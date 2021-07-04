package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.minestom.server.utils.BlockPosition
import java.lang.Exception

@Serializer(forClass = Sound::class)
@OptIn(ExperimentalSerializationApi::class)
object SoundSerializer : KSerializer<Sound> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Sound") {
            element<String>("name")
            element<String>("source")
            element<Float>("volume")
            element<Float>("pitch")
        }


    override fun serialize(encoder: Encoder, value: Sound) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name().value())
            encodeStringElement(descriptor, 1, value.source().name)
            encodeFloatElement(descriptor, 2, value.volume())
            encodeFloatElement(descriptor, 3, value.pitch())
        }
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): Sound =
        decoder.decodeStructure(descriptor) {
            var name = ""
            var source = ""
            var volume = 0f
            var pitch = 0f

            if(decodeSequentially()) {
                name = decodeStringElement(descriptor, 0)
                source = decodeStringElement(descriptor, 1)
                volume = decodeFloatElement(descriptor, 2)
                pitch = decodeFloatElement(descriptor, 3)
            } else while (true) {
                when(val index = decodeElementIndex((descriptor))) {
                    0 -> name = decodeStringElement(descriptor, 0)
                    1 -> source = decodeStringElement(descriptor, 1)
                    2 -> volume = decodeFloatElement(descriptor, 2)
                    3 -> pitch = decodeFloatElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            val soundSource = try {
               Sound.Source.valueOf(source)
            } catch (exception: Exception) {
                error("Sound source is invalid")
            }

            Sound.sound(Key.key(name), soundSource, volume, pitch)
        }

}