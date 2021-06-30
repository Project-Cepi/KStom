package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.utils.time.TimeUnit
import net.minestom.server.utils.time.UpdateOption
import java.lang.IllegalArgumentException

@Serializer(forClass = UpdateOption::class)
@OptIn(ExperimentalSerializationApi::class)
object UpdateOptionSerializer : KSerializer<UpdateOption> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("UpdateOption") {
        element<Long>("length")
        element<String>("unit")
    }

    override fun serialize(encoder: Encoder, value: UpdateOption) {
        encoder.encodeStructure(descriptor) {
            encodeLongElement(descriptor, 0, value.value)
            encodeStringElement(descriptor, 1, value.timeUnit.toString())
        }
    }

    override fun deserialize(decoder: Decoder): UpdateOption {
        return decoder.decodeStructure(descriptor) {
            var length = 0L
            var unit = TimeUnit.TICK
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> length = decodeLongElement(descriptor, 0)
                    1 -> {

                        val string = decodeStringElement(descriptor, 1)

                        unit = try {
                            TimeUnit.valueOf(string)
                        } catch (exception: IllegalArgumentException) {
                            error("Unexpected time unit: $string")
                        }
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            require(length in 0..Long.MAX_VALUE)
            UpdateOption(length, unit)
        }
    }
}