package world.cepi.kstom.nbt

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.*

internal fun <T> NbtFormat.readNbt(element: NBT, deserializer: DeserializationStrategy<T>): T {
    val input = when (element) {
        is NBTCompound -> TagDecoder(this, element)
        is NBTList<*> -> TagListDecoder(this, element)
        else -> TagPrimitiveDecoder(this, element)
    }
    return input.decodeSerializableValue(deserializer)
}

internal inline fun <reified T : NBT> cast(obj: NBT): T {
    check(obj is T) { "Expected ${T::class} but found ${obj::class}" }
    return obj
}

private inline fun <reified T> Any.cast() = this as T


@OptIn(ExperimentalSerializationApi::class)
private sealed class AbstractTagDecoder(val format: NbtFormat, open val map: NBT) : NamedValueTagDecoder() {

    override val serializersModule: SerializersModule
        get() = format.serializersModule


    private fun currentObject() = currentTagOrNull?.let { currentElement(it) } ?: map


    override fun composeName(parentName: String, childName: String): String = childName

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        val currentObject = currentObject()
        return when (descriptor.kind) {
            StructureKind.LIST -> {
                if (descriptor.kind == StructureKind.LIST && descriptor.getElementDescriptor(0).isNullable) NullableListDecoder(
                    format,
                    cast(currentObject)
                )
                else TagListDecoder(format, cast(currentObject))
            }
            is PolymorphicKind -> TagMapDecoder(format, cast(currentObject))
            StructureKind.MAP -> format.selectMapMode(
                descriptor,
                { TagMapDecoder(format, cast(currentObject)) },
                { TagListDecoder(format, cast(currentObject)) }
            )
            else -> TagDecoder(format, cast(currentObject))
        }
    }

    protected open fun getValue(tag: String): NBT = currentElement(tag)

    protected abstract fun currentElement(tag: String): NBT

    override fun decodeTaggedChar(tag: String): Char {
        val o = getValue(tag) as? NBTString ?: throw SerializationException("Non-string can't be represented as Char")
        val str = o.value
        return if (str.length == 1) str[0] else throw SerializationException("$o can't be represented as Char")
    }

    override fun decodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor): Int =
        enumDescriptor.getElementIndex(getValue(tag).toString())

    override fun decodeTaggedNull(tag: String): Nothing? = null

    override fun decodeTaggedNotNullMark(tag: String): Boolean =
        (currentElement(tag) as? NBTByte)?.value != NbtFormat.Null

//    override fun decodeTaggedUnit(tag: String) {
//        return
//    }

    override fun decodeTaggedBoolean(tag: String): Boolean = decodeTaggedByte(tag) == 1.toByte()
    override fun decodeTaggedByte(tag: String): Byte = getNumberValue(tag, { value as Byte }, { toByte() })
    override fun decodeTaggedShort(tag: String) = getNumberValue(tag, { value as Short }, { toShort() })
    override fun decodeTaggedInt(tag: String): Int = getNumberValue(tag, { value as Int }, { toInt() })

    override fun decodeTaggedLong(tag: String) = getNumberValue(tag, { value as Long }, { toLong() })
    override fun decodeTaggedFloat(tag: String) = getNumberValue(tag, { value as Float }, { toFloat() })
    override fun decodeTaggedDouble(tag: String) = getNumberValue(tag, { value as Double }, { toDouble() })
    override fun decodeTaggedString(tag: String): String = getValue(tag).cast<NBTString>().value

    override fun decodeTaggedTag(key: String): NBT = getValue(key)

    private inline fun <T> getNumberValue(
        tag: String,
        getter: NBTNumber<*>.() -> T,
        stringGetter: String.() -> T
    ): T {
        val value = getValue(tag)
        if (value is NBTNumber<*>) return value.getter()
        else return value.cast<NBTString>().value.stringGetter()
    }
}


private class TagPrimitiveDecoder(json: NbtFormat, override val map: NBT) : AbstractTagDecoder(json, map) {

    init {
        pushTag(PRIMITIVE_TAG)
    }
    override fun decodeElementIndex(descriptor: SerialDescriptor): Int  = 0

    override fun currentElement(tag: String): NBT {
        require(tag === PRIMITIVE_TAG) { "This input can only handle primitives with '$PRIMITIVE_TAG' tag" }
        return map
    }
}

private open class TagDecoder(json: NbtFormat, override val map: NBTCompound) : AbstractTagDecoder(json, map) {
    private var position = 0

    @OptIn(ExperimentalSerializationApi::class)
    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        while (position < descriptor.elementsCount) {
            val name = descriptor.getTag(position++)
            if (map.containsKey(name)) {
                return position - 1
            }
        }
        return CompositeDecoder.DECODE_DONE
    }

    override fun currentElement(tag: String): NBT = map.get(tag)!!

}

private class TagMapDecoder(json: NbtFormat, override val map: NBTCompound) : TagDecoder(json, map) {
    private val keys = map.getKeys().toList()
    private val size: Int = keys.size * 2
    private var position = -1

    override fun elementName(desc: SerialDescriptor, index: Int): String {
        val i = index / 2
        return keys[i]
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        while (position < size - 1) {
            position++
            return position
        }
        return CompositeDecoder.DECODE_DONE
    }

    override fun currentElement(tag: String): NBT {
        return if (position % 2 == 0) NBTString(tag) else map.get(tag)!!
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        // do nothing, maps do not have strict keys, so strict mode check is omitted
    }
}

private class NullableListDecoder(json: NbtFormat, override val map: NBTCompound) : TagDecoder(json, map) {
    private val size: Int = map.size
    private var position = -1

    override fun elementName(desc: SerialDescriptor, index: Int): String = index.toString()


    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        while (position < size - 1) {
            position++
            return position
        }
        return CompositeDecoder.DECODE_DONE
    }

    override fun currentElement(tag: String): NBT {
        return map[tag]!!
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        // do nothing, maps do not have strict keys, so strict mode check is omitted
    }
}


private class TagListDecoder(json: NbtFormat, override val map: NBTList<*>) : AbstractTagDecoder(json, map) {
    private val size = map.length
    private var currentIndex = -1

    override fun elementName(desc: SerialDescriptor, index: Int): String = (index).toString()

    override fun currentElement(tag: String): NBT {
        return map[tag.toInt()]
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        while (currentIndex < size - 1) {
            currentIndex++
            return currentIndex
        }
        return CompositeDecoder.DECODE_DONE
    }
}
