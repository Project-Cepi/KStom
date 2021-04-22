package world.cepi.kstom.nbt.compound

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.internal.NamedValueDecoder
import kotlinx.serialization.serializer
import org.jglrxavpok.hephaistos.nbt.*

@InternalSerializationApi
@ExperimentalSerializationApi
class NBTCompoundDecoder(val nbt: NBTCompound) : NamedValueDecoder() {
    private var elementIndex = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (elementIndex == descriptor.elementsCount) return CompositeDecoder.DECODE_DONE
        return elementIndex++
    }

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

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
fun <T> decodeFromCompoundNBT(compound: NBTCompound, deserializer: DeserializationStrategy<T>): T {
    val decoder = NBTCompoundDecoder(compound)
    return decoder.decodeSerializableValue(deserializer)
}

inline fun <reified T> decodeFromCompoundNBT(compound: NBTCompound): T = decodeFromCompoundNBT(compound, serializer())

inline fun <reified T> decodeFromNBT(nbt: NBT): T? {
    return when (nbt::class) {
        NBTInt::class -> (nbt as NBTInt).value as T
        NBTString::class -> (nbt as NBTString).value as T
        NBTLong::class -> (nbt as NBTLong).value as T
        NBTCompound::class -> {
            return try {
                decodeFromCompoundNBT<T>(nbt as NBTCompound)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        else -> null
    }
}