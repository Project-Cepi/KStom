package world.cepi.kstom.nbt

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.*
import java.lang.reflect.Field

internal fun <T> NbtFormat.writeNbt(value: T, serializer: SerializationStrategy<T>): NBT {
    lateinit var result: NBT
    val encoder = TagEncoder(this) { result = it }
    encoder.encodeSerializableValue(serializer, value)
    return result
}
@OptIn(ExperimentalSerializationApi::class)
private sealed class AbstractTagEncoder(
    val format: NbtFormat,
    val nodeConsumer: (NBT) -> Unit
) : NamedValueTagEncoder() {

    final override val serializersModule: SerializersModule
        get() = format.serializersModule


    private var writePolymorphic = false

    override fun composeName(parentName: String, childName: String): String = childName
    abstract fun putElement(key: String, element: NBT)
    abstract fun getCurrent(): NBT

    override fun encodeTaggedNull(tag: String) = putElement(tag, NBTByte(NbtFormat.Null))

    override fun encodeTaggedInt(tag: String, value: Int) = putElement(tag, NBTInt(value))
    override fun encodeTaggedByte(tag: String, value: Byte) = putElement(tag, NBTByte(value))
    override fun encodeTaggedShort(tag: String, value: Short) = putElement(tag, NBTShort(value))
    override fun encodeTaggedLong(tag: String, value: Long) = putElement(tag, NBTLong(value))
    override fun encodeTaggedFloat(tag: String, value: Float) = putElement(tag, NBTFloat(value))
    override fun encodeTaggedDouble(tag: String, value: Double) = putElement(tag, NBTDouble(value))

    override fun encodeTaggedBoolean(tag: String, value: Boolean) =
        putElement(tag, NBTByte(value))

    override fun encodeTaggedChar(tag: String, value: Char) = putElement(tag, NBTString(value.toString()))
    override fun encodeTaggedString(tag: String, value: String) = putElement(tag, NBTString(value))

    override fun encodeTaggedEnum(
        tag: String,
        enumDescriptor: SerialDescriptor,
        ordinal: Int
    ) = putElement(tag, NBTString(enumDescriptor.getElementName(ordinal)))


    override fun encodeTaggedTag(key: String, tag: NBT) = putElement(key, tag)

    override fun encodeTaggedValue(tag: String, value: Any) {
        putElement(tag, NBTString(value.toString()))
    }

    override fun elementName(descriptor: SerialDescriptor, index: Int): String {
        return if (descriptor.kind is PolymorphicKind) index.toString() else super.elementName(descriptor, index)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        val consumer = if (currentTagOrNull == null) nodeConsumer
        else { node -> putElement(currentTag, node) }

        val encoder = when (descriptor.kind) {
            StructureKind.LIST -> {
                if (descriptor.kind == StructureKind.LIST && descriptor.getElementDescriptor(0).isNullable) NullableListEncoder(format, consumer)
                else TagListEncoder(format, consumer)
            }
            is PolymorphicKind -> TagMapEncoder(format, consumer)
            StructureKind.MAP -> format.selectMapMode(descriptor,
                ifMap = { TagMapEncoder(format, consumer) },
                ifList = { TagListEncoder(format, consumer) }
            )
            else -> TagEncoder(format, consumer)
        }

        if (writePolymorphic) {
            writePolymorphic = false
            encoder.putElement(ClassDiscriminator, NBTString(descriptor.serialName))
        }

        return encoder
    }

    override fun endEncode(descriptor: SerialDescriptor) {
        nodeConsumer(getCurrent())
    }
}


internal const val PRIMITIVE_TAG = "primitive" // also used in drawer.nbt.JsonPrimitiveInput

private open class TagEncoder(format: NbtFormat, nodeConsumer: (NBT) -> Unit) :
    AbstractTagEncoder(format, nodeConsumer) {

    protected val content: NBTCompound = NBTCompound()

    override fun putElement(key: String, element: NBT) {
        content[key] = element
    }

    override fun getCurrent(): NBT = content
}

private class TagMapEncoder(format: NbtFormat, nodeConsumer: (NBT) -> Unit) : TagEncoder(format, nodeConsumer) {
    private lateinit var key: String

    override fun putElement(key: String, element: NBT) {
        val idx = key.toInt()
        // writing key
        when {
            idx % 2 == 0 -> this.key = when (element) {
                is NBTCompound, is NBTList<*>, is NBTEnd -> throw compoundTagInvalidKeyKind(
                    when (element) {
                        is NBTCompound -> ForCompoundTag.descriptor
                        is NBTList<*> -> ForListTag.descriptor
                        is NBTEnd -> ForEndTag.descriptor
                        else -> error("impossible")
                    }
                )
                else -> element.toString()
            }
            else -> content[this.key] = element
        }
    }

    override fun getCurrent(): NBT = content

}

private class NullableListEncoder(format: NbtFormat, nodeConsumer: (NBT) -> Unit) : TagEncoder(format, nodeConsumer) {
    override fun putElement(key: String, element: NBT) {
        content[key] = element
    }

    override fun getCurrent(): NBT = content

}

private class TagListEncoder(json: NbtFormat, nodeConsumer: (NBT) -> Unit) :
    AbstractTagEncoder(json, nodeConsumer) {
    private val list: NBTList<NBT> = NBTList(0)

    override fun elementName(descriptor: SerialDescriptor, index: Int): String = index.toString()


    override fun putElement(key: String, element: NBT) {
        list.add(element)
    }

    override fun getCurrent(): NBT = list
}
