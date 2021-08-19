package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializer(forClass = ItemStack::class)
@OptIn(ExperimentalSerializationApi::class)
object ItemStackSerializer : KSerializer<ItemStack> {
    override val descriptor = buildClassSerialDescriptor("ItemStack") {
        element<String>("material")
        element<Int>("amount")
        element<NBT>("nbt")
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.material.name())
            encodeIntElement(descriptor, 1, value.amount)
            encodeSerializableElement(descriptor, 2, NBTSerializer, value.meta.toNBT())
        }
    }

    override fun deserialize(decoder: Decoder): ItemStack = decoder.decodeStructure(descriptor) {
        var material: Material? = null
        var meta: NBT? = null
        var amount: Int? = null
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> material = Material.fromNamespaceId(decodeStringElement(descriptor, 0))
                1 -> amount = decodeIntElement(descriptor, 1)
                2 -> meta = decodeSerializableElement(descriptor, 2, NBTSerializer)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }
        ItemStack.fromNBT(material!!, meta!! as NBTCompound, amount!!)
    }
}