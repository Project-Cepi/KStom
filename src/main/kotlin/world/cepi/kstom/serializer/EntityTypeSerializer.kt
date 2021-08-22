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
object EntityTypeSerializer : AbstractProtocolObjectSerializer<EntityType>(EntityType::class) {
    override fun deserialize(decoder: Decoder): EntityType = EntityType.fromNamespaceId(decoder.decodeString())
}