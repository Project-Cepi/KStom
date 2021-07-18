package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.Position
import net.minestom.server.utils.Vector
import org.jetbrains.annotations.Nullable
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializer(forClass = Position::class)
@OptIn(ExperimentalSerializationApi::class)
object ItemStackSerializer : KSerializer<ItemStack> {
    override val descriptor = buildClassSerialDescriptor("ItemStack") {
        element<Material>("material")
        element<NBTSerializer.SerializableNBT>("nbt")
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.material.name)
            encodeSerializableElement(descriptor, 1, NBTSerializer, value.meta.toNBT())
        }
    }

    override fun deserialize(decoder: Decoder): ItemStack = decoder.decodeStructure(descriptor) {
        var material: Material? = null
        var meta: NBT? = null
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> material = Material.valueOf(decodeStringElement(descriptor, 0))
                1 -> meta = decodeSerializableElement(descriptor, 1, NBTSerializer)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }
        ItemStack.fromNBT(material!!, meta!! as NBTCompound)
    }
}