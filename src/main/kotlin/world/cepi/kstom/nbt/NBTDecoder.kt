package world.cepi.kstom.nbt

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.internal.NamedValueDecoder
import kotlinx.serialization.serializer
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@InternalSerializationApi
@ExperimentalSerializationApi
class NBTDecoder(val nbt: NBTCompound) : NamedValueDecoder() {
    private var elementIndex = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int = 0

    override fun decodeTaggedBoolean(tag: String): Boolean = nbt.getAsByte(tag) != 0.toByte()
    override fun decodeTaggedByte(tag: String): Byte = nbt.getAsByte(tag)!!
    override fun decodeTaggedChar(tag: String): Char = nbt.getString(tag)!!.toCharArray()[0]
    override fun decodeTaggedDouble(tag: String): Double = nbt.getAsDouble(tag)!!
    override fun decodeTaggedFloat(tag: String): Float = nbt.getAsFloat(tag)!!
    override fun decodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor): Int = enumDescriptor.getElementIndex(nbt.getString(tag)!!)
    override fun decodeTaggedShort(tag: String): Short = nbt.getAsShort(tag)!!
    override fun decodeTaggedLong(tag: String): Long = nbt.getAsLong(tag)!!
    override fun decodeTaggedString(tag: String): String = nbt.getString(tag)!!
    override fun decodeTaggedInt(tag: String): Int = nbt.getAsInt(tag)!!
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
