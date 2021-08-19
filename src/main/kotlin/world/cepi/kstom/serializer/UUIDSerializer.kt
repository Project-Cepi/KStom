package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import java.util.*

object UUIDSerializer : KSerializer<UUID> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("UUID") {
            element<Long>("least")
            element<Long>("most")
        }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeStructure(SoundSerializer.descriptor) {
            encodeLongElement(descriptor, 0, value.leastSignificantBits)
            encodeLongElement(descriptor, 1, value.mostSignificantBits)
        }
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): UUID =
        decoder.decodeStructure(SoundSerializer.descriptor) {
            var least = 0L
            var most = 0L
            while (true) {
                when (val index = decodeElementIndex(PositionSerializer.descriptor)) {
                    0 -> least = decodeLongElement(descriptor, 0)
                    1 -> most = decodeLongElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            UUID(least, most)
        }

}