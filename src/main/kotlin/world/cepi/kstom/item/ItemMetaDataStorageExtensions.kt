package world.cepi.kstom.item

import kotlinx.serialization.*
import java.util.*
import kotlinx.serialization.modules.SerializersModule
import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagReadable
import net.minestom.server.tag.TagWritable
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.nbt.NBTParser
import world.cepi.kstom.nbt.NBTFormat
import kotlin.reflect.KClass

@OptIn(InternalSerializationApi::class)
operator fun <T: @Serializable Any> TagWritable.set(
    tag: String,
    clazz: KClass<T>,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = clazz.serializer(),
    item: @Serializable T
) {
    if (module == null)
        setTag(Tag.NBT(tag), NBTParser.serialize(serializer, item))
    else
        setTag(Tag.NBT(tag), NBTFormat(module).serialize(serializer, item))
}

@OptIn(InternalSerializationApi::class)
inline operator fun <reified T: @Serializable Any> TagWritable.set(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = T::class.serializer(),
    item: @Serializable T
) = set(tag, T::class, module, serializer, item)

// TODO convert to TagWithable or something when its an interface

@OptIn(InternalSerializationApi::class)
fun <T: @Serializable Any> Block.with(
    tag: String,
    clazz: KClass<T>,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = clazz.serializer(),
    item: @Serializable T
): Block = if (module == null)
        withTag(Tag.NBT(tag), NBTParser.serialize(serializer, item))
    else
        withTag(Tag.NBT(tag), NBTFormat(module).serialize(serializer, item))

@OptIn(InternalSerializationApi::class)
inline fun <reified T: @Serializable Any> Block.with(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = T::class.serializer(),
    item: @Serializable T
): Block = with(tag, T::class, module, serializer, item)

@OptIn(InternalSerializationApi::class)
fun <T: @Serializable Any> TagReadable.get(
    tag: String,
    clazz: KClass<T>,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = clazz.serializer()
): T? = this.getTag(Tag.NBT(tag))?.let {
    return@let (
            if (module == null)
                NBTParser
            else
                NBTFormat(module)
            )
        .deserialize(serializer, it as? NBTCompound ?: return null)
}

@OptIn(InternalSerializationApi::class)
public inline fun <reified T: @Serializable Any> TagReadable.get(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = T::class.serializer()
): T? = this.get(tag, T::class, module, serializer)