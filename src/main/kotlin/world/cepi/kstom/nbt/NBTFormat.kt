package world.cepi.kstom.nbt

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import org.jglrxavpok.hephaistos.nbt.*
import java.lang.IllegalArgumentException

internal val TagModule = SerializersModule {
    polymorphic(NBT::class) {
        subclass(NBTByte::class, ForByteTag)
        subclass(NBTShort::class, ForShortTag)
        subclass(NBTInt::class, ForIntTag)
        subclass(NBTLong::class, ForLongTag)
        subclass(NBTFloat::class, ForFloatTag)
        subclass(NBTDouble::class, ForDoubleTag)
        subclass(NBTString::class, ForStringTag)
        subclass(NBTEnd::class, ForEndTag)
        subclass(NBTByteArray::class, ForByteArrayTag)
        subclass(NBTIntArray::class, ForIntArrayTag)
        subclass(NBTLongArray::class, ForLongArrayTag)
        subclass(NBTList::class, ForListTag)
        subclass(NBTCompound::class, ForCompoundTag)
    }
}


/**
 * Keeping this class public for now in case you want to serializer an object directly to tag and vise versa.
 */
@OptIn(ExperimentalSerializationApi::class)
open class NbtFormat(context: SerializersModule = EmptySerializersModule) : SerialFormat {

    /**
     * Converts [obj] into a [CompoundTag] that represents [obj].
     * Later [deserialize] can be called to retrieve an identical instance of [obj] from the [CompoundTag].
     *
     * These functions are not documented because I think they would be confusing.
     * Do you want these to be an official part of the API? Please make an issue.
     */
    fun <T> serialize(serializer: SerializationStrategy<T>, obj: T): NBT {
        return writeNbt(obj, serializer)
    }

    inline fun <reified T> serialize(obj: T): NBT {
        return serialize(serializer(), obj)
    }

    fun <T> deserialize(deserializer: DeserializationStrategy<T>, tag: NBT): T {
        return readNbt(tag, deserializer)
    }

    inline fun <reified T> deserialize(tag: NBT): T {
        return deserialize(serializer(), tag)
    }

    internal companion object {
        const val Null = 1.toByte()
    }

    override val serializersModule = context + TagModule

}

object NBTParser : NbtFormat()

internal const val ClassDiscriminator = "type"

@OptIn(ExperimentalSerializationApi::class)
internal fun compoundTagInvalidKeyKind(keyDescriptor: SerialDescriptor) = IllegalArgumentException(
    "Value of type ${keyDescriptor.serialName} can't be used in a compound tag as map key. " +
            "It should have either primitive or enum kind, but its kind is ${keyDescriptor.kind}."
)

// This is an extension in case we want to have an option to not allow lists
@OptIn(ExperimentalSerializationApi::class)
internal inline fun <T, R1 : T, R2 : T> NbtFormat.selectMapMode(
    mapDescriptor: SerialDescriptor,
    ifMap: () -> R1,
    ifList: () -> R2
): T {
    val keyDescriptor = mapDescriptor.getElementDescriptor(0)
    val keyKind = keyDescriptor.kind
    return if (keyKind is PrimitiveKind || keyKind == SerialKind.ENUM) {
        ifMap()
    } else {
        ifList()
    }
}

