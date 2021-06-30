package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.utils.BlockPosition

@Serializer(forClass = BlockPosition::class)
@OptIn(ExperimentalSerializationApi::class)
object BlockPositionSerializer : KSerializer<BlockPosition> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("net.minestom.server.utils.BlockPosition") {
            element<Int>("x")
            element<Int>("y")
            element<Int>("z")
        }


    override fun serialize(encoder: Encoder, value: BlockPosition) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.x)
            encodeIntElement(descriptor, 1, value.y)
            encodeIntElement(descriptor, 2, value.z)
        }
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): BlockPosition =
        decoder.decodeStructure(descriptor) {
            var x = 0
            var y = 0
            var z = 0

            if(decodeSequentially()) {
                x = decodeIntElement(descriptor, 0)
                y = decodeIntElement(descriptor, 1)
                z = decodeIntElement(descriptor, 2)
            } else while (true) {
                when(val index = decodeElementIndex((descriptor))) {
                    0 -> x = decodeIntElement(descriptor, 0)
                    1 -> y = decodeIntElement(descriptor, 1)
                    2 -> z = decodeIntElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            BlockPosition(x, y, z)
        }

}