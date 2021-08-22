package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.Decoder
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material

@OptIn(ExperimentalSerializationApi::class)
object MaterialSerializer : AbstractProtocolObjectSerializer<Material>(Material::class) {
    override fun deserialize(decoder: Decoder): Material = Material.fromNamespaceId(decoder.decodeString())!!
}