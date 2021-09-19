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
        element<Int>("type")
        element<Boolean>("relativeX")
        element<Boolean>("relativeY")
        element<Boolean>("relativeZ")
    }

    override fun serialize(encoder: Encoder, value: RelativeVec) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, VectorSerializer, value.from(Pos.ZERO))
            encodeIntElement(descriptor, 1, value.coordinateType().ordinal)
            encodeBooleanElement(descriptor, 2, value.isRelativeX)
            encodeBooleanElement(descriptor, 3, value.isRelativeY)
            encodeBooleanElement(descriptor, 4, value.isRelativeZ)
        }
    }

    override fun deserialize(decoder: Decoder): RelativeVec {
        return decoder.decodeStructure(descriptor) {
            var vec: Vec? = null
            var coordinateType: RelativeVec.CoordinateType? = null
            var relativeX: Boolean? = null
            var relativeY: Boolean? = null
            var relativeZ: Boolean? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> vec = decodeSerializableElement(descriptor, 0, VectorSerializer)
                    1 -> coordinateType = RelativeVec.CoordinateType.values()[decodeIntElement(descriptor, 1)]
                    2 -> relativeX = decodeBooleanElement(descriptor, 2)
                    3 -> relativeY = decodeBooleanElement(descriptor, 3)
                    4 -> relativeZ = decodeBooleanElement(descriptor, 4)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            RelativeVec(vec!!, coordinateType!!, relativeX!!, relativeY!!, relativeZ!!)
        }
    }
}