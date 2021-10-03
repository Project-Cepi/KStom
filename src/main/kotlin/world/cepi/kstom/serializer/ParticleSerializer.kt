package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.*
import net.minestom.server.particle.Particle

@Serializer(forClass = Particle::class)
@OptIn(ExperimentalSerializationApi::class)
object ParticleSerializer : AbstractProtocolObjectSerializer<Particle>(Particle::class) {
    override fun deserialize(decoder: Decoder): Particle = Particle.fromNamespaceId(decoder.decodeString())!!
}