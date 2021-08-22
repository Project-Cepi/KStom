package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minestom.server.utils.NamespaceID

@Serializer(forClass = NamespaceID::class)
@OptIn(ExperimentalSerializationApi::class)
object NamespaceIDSerializer : KSerializer<NamespaceID> {

    override val descriptor = PrimitiveSerialDescriptor("NamespaceID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: NamespaceID) {
        encoder.encodeString(value.value())
    }

    override fun deserialize(decoder: Decoder): NamespaceID {
        return NamespaceID.from(decoder.decodeString())
    }

}