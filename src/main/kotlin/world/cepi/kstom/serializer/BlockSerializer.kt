package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.NamespaceID
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializer(forClass = Block::class)
@OptIn(ExperimentalSerializationApi::class)
object BlockSerializer : KSerializer<Block> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Block") {
        element("namespace", NamespaceIDSerializer.descriptor)
        element<NBT>("nbt")
    }

    override fun serialize(encoder: Encoder, value: Block) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, NamespaceIDSerializer, value.namespace())
            encodeSerializableElement(descriptor, 1, NBTSerializer, value.nbt() ?: NBTCompound() as NBT)
        }
    }

    override fun deserialize(decoder: Decoder): Block = decoder.decodeStructure(descriptor) {
        var namespaceID: NamespaceID? = null
        var nbt: NBTCompound? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> namespaceID = decodeSerializableElement(descriptor, 0, NamespaceIDSerializer)
                1 -> nbt = decodeSerializableElement(descriptor, 1, NBTSerializer) as? NBTCompound ?: NBTCompound()
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }

        namespaceID?.let { Block.fromNamespaceId(it)?.withNbt(nbt) } ?: Block.AIR
    }
}