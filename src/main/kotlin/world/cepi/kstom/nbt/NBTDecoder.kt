package world.cepi.kstom.nbt

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.internal.NamedValueDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@InternalSerializationApi
@ExperimentalSerializationApi
class NBTDecoder(val nbt: NBTCompound) : NamedValueDecoder() {
    private var elementIndex = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int = 0

    override fun decodeTaggedBoolean(tag: String): Boolean = nbt.getAsByte(tag) != 0.toByte()
    override fun decodeTaggedByte(tag: String): Byte = nbt.getAsByte(tag)!!
}

@InternalSerializationApi
@ExperimentalSerializationApi
fun <T> decodeFromList(compound: NBTCompound, deserializer: DeserializationStrategy<T>): T {
    val decoder = NBTDecoder(compound)
    return decoder.decodeSerializableValue(deserializer)
}

@InternalSerializationApi
@ExperimentalSerializationApi
inline fun <reified T> decodeFromList(compound: NBTCompound): T = decodeFromList(compound, serializer())
