package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.Decoder
import net.minestom.server.instance.block.Block

@OptIn(ExperimentalSerializationApi::class)
object BlockSerializer : AbstractProtocolObjectSerializer<Block>(Block::class) {
    override fun deserialize(decoder: Decoder): Block = Block.fromNamespaceId(decoder.decodeString())!!
}