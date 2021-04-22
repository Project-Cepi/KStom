package world.cepi.kstom.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind

@OptIn(ExperimentalSerializationApi::class)
internal sealed class PublicedListLikeDescriptor(val elementDesc: SerialDescriptor) : SerialDescriptor {
    override val kind: SerialKind get() = StructureKind.LIST
    override val elementsCount: Int = 1

    override fun getElementName(index: Int): String = index.toString()
    override fun getElementIndex(name: String): Int =
        name.toIntOrNull() ?: throw IllegalArgumentException("$name is not a valid list index")

    override fun isElementOptional(index: Int): Boolean {
        if (index != 0) throw IllegalStateException("List descriptor has only one child element, index: $index")
        return false
    }

    override fun getElementAnnotations(index: Int): List<Annotation> {
        if (index != 0) throw IndexOutOfBoundsException("List descriptor has only one child element, index: $index")
        return emptyList()
    }

    override fun getElementDescriptor(index: Int): SerialDescriptor {
        if (index != 0) throw IndexOutOfBoundsException("List descriptor has only one child element, index: $index")
        return elementDesc
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PublicedListLikeDescriptor) return false
        if (elementDesc == other.elementDesc && serialName == other.serialName) return true
        return false
    }

    override fun hashCode(): Int {
        return elementDesc.hashCode() * 31 + serialName.hashCode()
    }
}
@OptIn(ExperimentalSerializationApi::class)
internal open class PublicedListLikeDescriptorImpl(elementDesc: SerialDescriptor, override val serialName : String) : PublicedListLikeDescriptor(elementDesc)
