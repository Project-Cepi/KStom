package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import java.time.Duration

@Serializer(forClass = Duration::class)
@OptIn(ExperimentalSerializationApi::class)
object DurationSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("UpdateOption") {
        element<Long>("milliseconds")
    }

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeStructure(descriptor) {
            encodeLongElement(descriptor, 0, value.toMillis())
        }
    }

    override fun deserialize(decoder: Decoder): Duration {
        return decoder.decodeStructure(descriptor) {
            var length = 0L
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> length = decodeLongElement(descriptor, 0)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            require(length in 0..Long.MAX_VALUE)
            Duration.ofMillis(length)
        }
    }
}