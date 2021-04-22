package world.cepi.kstom.nbt

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.internal.NamedValueDecoder
import kotlinx.serialization.internal.NamedValueEncoder
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@OptIn(InternalSerializationApi::class)
internal abstract class NamedValueTagEncoder : NamedValueEncoder(), ICanEncodeTag {
    final override fun encodeTag(tag: NBT) = encodeTaggedTag(popTag(), tag)
    abstract fun encodeTaggedTag(key: String, tag: NBT)
}

@OptIn(InternalSerializationApi::class)
internal abstract class NamedValueTagDecoder : NamedValueDecoder(), ICanDecodeTag {
    final override fun decodeTag(): NBT = decodeTaggedTag(popTag())
    abstract fun decodeTaggedTag(key: String): NBT
}

internal interface ICanEncodeTag : ICanEncodeCompoundTag {
    fun encodeTag(tag: NBT)
    override fun encodeCompoundTag(tag: NBTCompound) = encodeTag(tag)
}

internal interface ICanDecodeTag : ICanDecodeCompoundTag {
    fun decodeTag(): NBT
    override fun decodeCompoundTag(): NBTCompound = decodeTag() as NBTCompound
}

internal interface ICanEncodeCompoundTag {
    fun encodeCompoundTag(tag: NBTCompound)
}

internal interface ICanDecodeCompoundTag {
    fun decodeCompoundTag(): NBTCompound
}