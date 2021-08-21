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
import net.minestom.server.coordinate.Vec

@Serializer(forClass = BossBar::class)
@OptIn(ExperimentalSerializationApi::class)
object BossBarSerializer : KSerializer<BossBar> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("BossBar") {
        element<Component>("name")
        element<Float>("progress")
        element<Int>("color")
        element<Int>("overlay")
    }

    override fun serialize(encoder: Encoder, value: BossBar) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, ComponentSerializer, value.name())
            encodeFloatElement(descriptor, 1, value.progress())
            encodeIntElement(descriptor, 2, value.color().ordinal)
            encodeIntElement(descriptor, 3, value.overlay().ordinal)
        }
    }

    override fun deserialize(decoder: Decoder): BossBar {
        return decoder.decodeStructure(descriptor) {
            var text: Component = Component.empty()
            var progress = 0f
            var color = 0
            var overlay = 0
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> text = decodeSerializableElement(descriptor,0, ComponentSerializer)
                    1 -> progress = decodeFloatElement(descriptor, 1)
                    2 -> color = decodeIntElement(descriptor, 2)
                    3 -> overlay = decodeIntElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            BossBar.bossBar(text, progress, BossBar.Color.values()[color], BossBar.Overlay.values()[overlay])
        }
    }
}