package world.cepi.kstom.item

import kotlinx.serialization.*
import java.util.*
import kotlinx.serialization.modules.SerializersModule
import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagReadable
import net.minestom.server.tag.TagWritable
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.nbt.NBTParser
import world.cepi.kstom.nbt.NBTFormat
import kotlin.reflect.KClass

inline operator fun <reified T: @Serializable Any> TagWritable.set(tag: String, item: @Serializable T) {
    setTag(Tag.NBT(tag), NBTParser.serialize(item))
}

inline operator fun <reified T: @Serializable Any> TagWritable.set(tag: String, module: SerializersModule, item: @Serializable T) {
    setTag(Tag.NBT(tag), NBTFormat(module).serialize(item))
}

inline operator fun <reified T: @Serializable Any> TagWritable.set(tag: String, serializer: KSerializer<T>, item: @Serializable T) {
    setTag(Tag.NBT(tag), NBTParser.serialize(serializer, item))
}

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
inline fun <reified T: @Serializable Any> TagReadable.get(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = T::class.serializer()
): T? = this.get(tag, T::class, module, serializer)