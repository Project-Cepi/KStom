package world.cepi.kstom.serializer

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.internal.AbstractPolymorphicSerializer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.EntityType
import net.minestom.server.utils.NamespaceID
import javax.xml.stream.events.Namespace
import kotlin.reflect.KClass

@OptIn(ExperimentalSerializationApi::class)
object EntityTypeSerializer : KSerializer<EntityType> {

    override val descriptor = PrimitiveSerialDescriptor("net.minestom.server.entity.EntityType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: EntityType) = encoder.encodeString(value.namespace().asString())

    override fun deserialize(decoder: Decoder): EntityType = EntityType.fromNamespaceId(decoder.decodeString())
}

@Serializer(forClass = EntityType::class)
@OptIn(ExperimentalSerializationApi::class)
object PolyEntityTypeSerializer : KSerializer<EntityType> {

    @InternalSerializationApi
    override val descriptor =
        buildSerialDescriptor("PolymorphicEntityType", SerialKind.CONTEXTUAL) {
            element("name", NamespaceIDSerializer.descriptor)
        }

    override fun serialize(encoder: Encoder, value: EntityType) {
        EntityTypeSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): EntityType {
        return EntityTypeSerializer.deserialize(decoder)
    }

}