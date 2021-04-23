package world.cepi.kstom.nbt

import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.apache.logging.log4j.LogManager
import org.jglrxavpok.hephaistos.nbt.*

private val Logger = LogManager.getLogger("Fabric-Drawer")

private inline fun <T> missingField(missingField: String, deserializing: String, defaultValue: () -> T): T {
    Logger.warn("Missing $missingField while deserializing $deserializing")
    return defaultValue()
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTByte::class)
object ForByteTag : KSerializer<NBTByte> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ByteTag", PrimitiveKind.BYTE)
    override fun serialize(encoder: Encoder, value: NBTByte) = encoder.encodeByte(value.value)
    override fun deserialize(decoder: Decoder): NBTByte = NBTByte(decoder.decodeByte())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTShort::class)
object ForShortTag : KSerializer<NBTShort> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ShortTag", PrimitiveKind.SHORT)
    override fun serialize(encoder: Encoder, value: NBTShort) = encoder.encodeShort(value.value)
    override fun deserialize(decoder: Decoder): NBTShort = NBTShort(decoder.decodeShort())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTInt::class)
object ForIntTag : KSerializer<NBTInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntTag",PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: NBTInt) = encoder.encodeInt(value.value)
    override fun deserialize(decoder: Decoder): NBTInt = NBTInt(decoder.decodeInt())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTLong::class)
object ForLongTag : KSerializer<NBTLong> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LongTag",PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: NBTLong) = encoder.encodeLong(value.value)
    override fun deserialize(decoder: Decoder): NBTLong = NBTLong(decoder.decodeLong())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTFloat::class)
object ForFloatTag : KSerializer<NBTFloat> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FloatTag",PrimitiveKind.FLOAT)
    override fun serialize(encoder: Encoder, value: NBTFloat) = encoder.encodeFloat(value.value)
    override fun deserialize(decoder: Decoder): NBTFloat = NBTFloat(decoder.decodeFloat())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTDouble::class)
object ForDoubleTag : KSerializer<NBTDouble> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DoubleTag",PrimitiveKind.DOUBLE)
    override fun serialize(encoder: Encoder, value: NBTDouble) = encoder.encodeDouble(value.value)
    override fun deserialize(decoder: Decoder): NBTDouble = NBTDouble(decoder.decodeDouble())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTString::class)
object ForStringTag : KSerializer<NBTString> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StringTag",PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: NBTString) = encoder.encodeString(value.value)
    override fun deserialize(decoder: Decoder): NBTString = NBTString(decoder.decodeString())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTEnd::class)
object ForEndTag : KSerializer<NBTEnd> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("EndTag",PrimitiveKind.BYTE)
    override fun serialize(encoder: Encoder, value: NBTEnd) = encoder.encodeByte(0)
    override fun deserialize(decoder: Decoder): NBTEnd = NBTEnd().also { decoder.decodeByte() }
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTByteArray::class)
object ForByteArrayTag : KSerializer<NBTByteArray> {
    override val descriptor: SerialDescriptor = PublicedListLikeDescriptorImpl(ForByteTag.descriptor, "ByteArrayTag")

    override fun serialize(encoder: Encoder, value: NBTByteArray) =
        ListSerializer(ForByteTag).serialize(encoder, value.value.map { NBTByte(it) })

    override fun deserialize(decoder: Decoder): NBTByteArray =
        NBTByteArray(ListSerializer(ForByteTag).deserialize(decoder).map { it.value }.toByteArray())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTIntArray::class)
object ForIntArrayTag : KSerializer<NBTIntArray> {
    override val descriptor: SerialDescriptor = PublicedListLikeDescriptorImpl(ForIntTag.descriptor, "IntArrayTag")

    override fun serialize(encoder: Encoder, value: NBTIntArray) =
        ListSerializer(ForIntTag).serialize(encoder, value.value.map { NBTInt(it) })

    override fun deserialize(decoder: Decoder): NBTIntArray =
        NBTIntArray(ListSerializer(ForIntTag).deserialize(decoder).map { it.value }.toIntArray())
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTLongArray::class)
object ForLongArrayTag : KSerializer<NBTLongArray> {
    override val descriptor: SerialDescriptor = PublicedListLikeDescriptorImpl(ForLongTag.descriptor, "LongArrayTag")

    override fun serialize(encoder: Encoder, value: NBTLongArray) =
        ListSerializer(ForLongTag).serialize(encoder, value.value.map { NBTLong(it) })

    override fun deserialize(decoder: Decoder): NBTLongArray =
        NBTLongArray(ListSerializer(ForLongTag).deserialize(decoder).map { it.value }.toLongArray())
}


@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBT::class)
object ForTag : KSerializer<NBT> {
    override val descriptor: SerialDescriptor =         buildSerialDescriptor("kotlinx.serialization.Polymorphic", PolymorphicKind.OPEN) {
        element("type", String.serializer().descriptor)
        element(
            "value",
            buildSerialDescriptor("kotlinx.serialization.Polymorphic<${NBT::class.simpleName}>", SerialKind.CONTEXTUAL)
        )
    }
    override fun serialize(encoder: Encoder, value: NBT) {
        if (encoder is ICanEncodeTag) encoder.encodeTag(value)
        else PolymorphicSerializer(NBT::class).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): NBT {
        return if (decoder is ICanDecodeTag) decoder.decodeTag()
        else PolymorphicSerializer(NBT::class).deserialize(decoder)
    }
}

/**
 * ListTag can only hold one type of tag
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTList::class)
object ForListTag : KSerializer<NBTList<*>> {
    override val descriptor: SerialDescriptor = PublicedListLikeDescriptorImpl(ForTag.descriptor, "ListTag")

    override fun serialize(encoder: Encoder, value: NBTList<*>) {
        ListSerializer(ForTag).serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): NBTList<*> {

        var nbtList: NBTList<NBT>? = null

        for (tag in ListSerializer(ForTag).deserialize(decoder)) {

            if (nbtList == null) {
                nbtList = NBTList(NBTTypes.getID(tag::class.java))
            }

            nbtList.add(tag)
        }

        return nbtList ?: NBTList<NBT>(0)

    }
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Serializer(forClass = NBTCompound::class)
object ForCompoundTag : KSerializer<NBTCompound> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CompoundTag"){
        mapSerialDescriptor(PrimitiveSerialDescriptor("Key", PrimitiveKind.STRING), ForTag.descriptor)
    }

    override fun serialize(encoder: Encoder, value: NBTCompound) {
        if (encoder is ICanEncodeCompoundTag) {
            encoder.encodeCompoundTag(value)
        } else {
            MapSerializer(String.serializer(), ForTag).serialize(
                encoder,
                value.getKeys().map { it to value[it]!! }.toMap()
            )
        }

    }

    override fun deserialize(decoder: Decoder): NBTCompound {
        if (decoder is ICanDecodeCompoundTag) {
            return decoder.decodeCompoundTag()
        }
        return NBTCompound().apply {
            for ((key, value) in MapSerializer(String.serializer(), ForTag).deserialize(decoder)) {
                set(key, value)
            }
        }
    }
}