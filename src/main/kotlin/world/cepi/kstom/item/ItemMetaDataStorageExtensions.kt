package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder
import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.minestom.server.item.ItemMeta
import net.minestom.server.tag.Tag
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.nbt.NBTParser
import world.cepi.kstom.nbt.NBTFormat
import kotlin.reflect.KClass

class ItemMetaClientData(val metaBuilder: ItemMetaBuilder) {

    inline operator fun <reified T: @Serializable Any> set(tag: String, item: @Serializable T) {
        metaBuilder.set(Tag.NBT(tag), NBTParser.serialize(item))
    }

    inline operator fun <reified T: @Serializable Any> set(tag: String, module: SerializersModule, item: @Serializable T) {
        metaBuilder.set(Tag.NBT(tag), NBTFormat(module).serialize(item))
    }
}

fun ItemMetaBuilder.clientData(receiver: ItemMetaClientData.() -> Unit) = ItemMetaClientData(this).receiver()

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
fun <T: @Serializable Any> ItemMeta.get(
    tag: String,
    clazz: KClass<T>,
    module: SerializersModule? = null
): T? = this.getTag(Tag.NBT(tag))?.let {
    return@let (
            if (module == null)
                NBTParser
            else
                NBTFormat(module)
            )
        .deserialize(clazz.serializer(), it as? NBTCompound ?: return null)
}

public inline fun <reified T: @Serializable Any> ItemMeta.get(
    tag: String,
    module: SerializersModule? = null
): T? = this.get(tag, T::class, module)