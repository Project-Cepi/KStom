package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.minestom.server.coordinate.Vec

@Serializer(forClass = Component::class)
@OptIn(ExperimentalSerializationApi::class)
object ComponentSerializer : KSerializer<Component> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("BossBar") {
        element<String>("data")
    }

    override fun serialize(encoder: Encoder, value: Component) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, GsonComponentSerializer.gson().serialize(value))
        }
    }

    override fun deserialize(decoder: Decoder): Component {
        return decoder.decodeStructure(descriptor) {
            var json = ""
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> json = decodeStringElement(descriptor, 0)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            GsonComponentSerializer.gson().deserialize(json)
        }
    }
}