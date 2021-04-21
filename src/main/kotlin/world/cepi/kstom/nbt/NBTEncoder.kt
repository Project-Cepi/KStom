package world.cepi.kstom.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.internal.NamedValueEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTInt

@InternalSerializationApi
@ExperimentalSerializationApi
class NBTEncoder : NamedValueEncoder() {
    val nbt = NBTCompound()

    override val serializersModule: SerializersModule = EmptySerializersModule

    override fun encodeTaggedBoolean(tag: String, value: Boolean): Unit = run { nbt.setByte(tag, if (value) 1 else 0) }
    override fun encodeTaggedByte(tag: String, value: Byte): Unit = run { nbt.setByte(tag, value) }
    override fun encodeTaggedInt(tag: String, value: Int): Unit = run { nbt.setInt(tag, value) }
    override fun encodeTaggedShort(tag: String, value: Short): Unit = run { nbt.setShort(tag, value) }
    override fun encodeTaggedLong(tag: String, value: Long): Unit = run { nbt.setLong(tag, value) }
    override fun encodeTaggedFloat(tag: String, value: Float): Unit = run { nbt.setFloat(tag, value) }
    override fun encodeTaggedDouble(tag: String, value: Double): Unit = run { nbt.setDouble(tag, value) }
    override fun encodeTaggedChar(tag: String, value: Char): Unit = run { nbt.setString(tag, value.toString()) }
    override fun encodeTaggedString(tag: String, value: String): Unit = run { nbt.setString(tag, value) }
    override fun encodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor, ordinal: Int): Unit = run { nbt.setInt(tag, ordinal) }
    override fun encodeTaggedNull(tag: String): Unit = run { nbt.setByte(tag, 0) }

    override fun encodeTaggedValue(tag: String, value: Any) {
        nbt.setString(tag, value.toString())
    }

    override fun encodeTaggedInline(tag: String, inlineDescriptor: SerialDescriptor): Encoder {
        return object : AbstractEncoder() {
            override val serializersModule = EmptySerializersModule

            override fun encodeInt(value: Int): Unit = encodeTaggedInt(tag, value)
            override fun encodeBoolean(value: Boolean) = encodeTaggedBoolean(tag, value)
        }
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        when(serializer) {
            Int.serializer() -> NBTInt(value as Int)
            else -> serializer.serialize(this, value)
        }
    }

}

@InternalSerializationApi
@ExperimentalSerializationApi
fun <T> encodeToNBT(serializer: SerializationStrategy<T>, value: T): NBTCompound {
    val encoder = NBTEncoder()
    encoder.encodeSerializableValue(serializer, value)
    return encoder.nbt
}

@InternalSerializationApi
@ExperimentalSerializationApi
inline fun <reified T> encodeToNBT(value: T) = encodeToNBT(serializer(), value)