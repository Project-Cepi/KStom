package world.cepi.kstom.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Encoder
import net.minestom.server.registry.ProtocolObject
import kotlin.reflect.KClass

abstract class AbstractProtocolObjectSerializer<T : ProtocolObject>(
    clazz: KClass<T>,
) : KSerializer<T> {

    override val descriptor = PrimitiveSerialDescriptor(clazz.qualifiedName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) = encoder.encodeString(value.namespace().asString())
}