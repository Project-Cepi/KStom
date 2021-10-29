package world.cepi.kstom.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.NamespaceID
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializable
internal data class BlockSurrogate(
    @Serializable(with = NamespaceIDSerializer::class)
    val namespaceId: NamespaceID,
    @Serializable(with = NBTSerializer::class)
    val blockNbt: NBT
)

object BlockSerializer : KSerializer<Block> {
    override val descriptor: SerialDescriptor = BlockSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): Block {
        val blockSurrogate = decoder.decodeSerializableValue(BlockSurrogate.serializer())

        val nbtCompound = blockSurrogate.blockNbt as? NBTCompound
        val block = Block.fromNamespaceId(blockSurrogate.namespaceId)

        return block?.let { block.withNbt(nbtCompound) } ?: Block.AIR
    }

    override fun serialize(encoder: Encoder, value: Block) {
        val blockSurrogate = BlockSurrogate(value.namespace(), value.nbt() ?: NBTCompound())
        encoder.encodeSerializableValue(BlockSurrogate.serializer(), blockSurrogate)
    }
}