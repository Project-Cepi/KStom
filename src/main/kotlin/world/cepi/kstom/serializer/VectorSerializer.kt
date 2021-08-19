package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.coordinate.Vec

@Serializer(forClass = Vec::class)
@OptIn(ExperimentalSerializationApi::class)
object VectorSerializer : KSerializer<Vec> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vector") {
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
    }

    override fun serialize(encoder: Encoder, value: Vec) {
        encoder.encodeStructure(descriptor) {
            encodeDoubleElement(descriptor, 0, value.x())
            encodeDoubleElement(descriptor, 1, value.y())
            encodeDoubleElement(descriptor, 2, value.z())
        }
    }

    override fun deserialize(decoder: Decoder): Vec {
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