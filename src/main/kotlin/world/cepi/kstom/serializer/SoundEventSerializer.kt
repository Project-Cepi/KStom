package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.Decoder
import net.minestom.server.instance.block.Block
import net.minestom.server.sound.SoundEvent

@OptIn(ExperimentalSerializationApi::class)
object SoundEventSerializer : AbstractProtocolObjectSerializer<SoundEvent>(SoundEvent::class) {
    override fun deserialize(decoder: Decoder): SoundEvent = SoundEvent.fromNamespaceId(decoder.decodeString())!!
}