package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.utils.location.RelativeVec

@Serializer(forClass = RelativeVec::class)
@OptIn(ExperimentalSerializationApi::class)
object RelativeVecSerializer : KSerializer<RelativeVec> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vector") {
        element<Vec>("vec")
        element<RelativeVec.CoordinateType>("type")
        element<Boolean>("relativeX")
        element<Boolean>("relativeY")
        element<Boolean>("relativeZ")
    }

    override fun serialize(encoder: Encoder, value: RelativeVec) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, VectorSerializer, value.from(Pos.ZERO))
            encodeIntElement(descriptor, 1, value.)
            encodeDoubleElement(descriptor, 2, value.z())
        }
    }

    override fun deserialize(decoder: Decoder): RelativeVec {
        return decoder.decodeStructure(descriptor) {
            var x = 0.0
            var y = 0.0
            var z = 0.0
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, 0)
                    1 -> y = decodeDoubleElement(descriptor, 1)
                    2 -> z = decodeDoubleElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            Vec(x, y, z)
        }
    }
}