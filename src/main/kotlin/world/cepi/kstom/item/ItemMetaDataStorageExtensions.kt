package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.minestom.server.item.ItemMeta
import net.minestom.server.tag.Tag
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.nbt.NBTParser
import world.cepi.kstom.nbt.NbtFormat
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.TYPEALIAS)
@RequiresOptIn("Literally doesn't garbage collect at all.", level = RequiresOptIn.Level.WARNING)
public annotation class ExperimentalServerStorageAPI

class ItemMetaClientData(val metaBuilder: ItemMetaBuilder) {

    inline operator fun <reified T: @Serializable Any> set(tag: String, item: @Serializable T) {
        metaBuilder.set(Tag.NBT(tag), NBTParser.serialize(item))
    }

    inline operator fun <reified T: @Serializable Any> set(tag: String, module: SerializersModule, item: @Serializable T) {
        metaBuilder.set(Tag.NBT(tag), NbtFormat(module).serialize(item))
    }
}

@ExperimentalServerStorageAPI
class ItemMetaServerDataProvider(val itemMetaBuilder: ItemMetaBuilder) {

    val uuid: UUID = UUID.randomUUID()

    operator fun <T> get(tag: String): T? {
        return ItemMetaServerData[uuid, tag]
    }

    operator fun set(tag: String, item: Any?) {
        ItemMetaServerData[uuid, tag] = item
    }
}

object ItemMetaServerData {

    private val map: MutableMap<UUID, MutableMap<String, Any?>> by lazy {
        ConcurrentHashMap()
    }

    operator fun <T> get(uuid: UUID, tag: String): T? {
        if (map[uuid] == null) map[uuid] = ConcurrentHashMap()
        return map[uuid]!![tag] as? T
    }

    operator fun set(uuid: UUID, tag: String, item: Any?) {
        if (map[uuid] == null) map[uuid] = ConcurrentHashMap()
        map[uuid]!![tag] = item
    }
}

public fun ItemMetaBuilder.clientData(receiver: ItemMetaClientData.() -> Unit) = ItemMetaClientData(this).receiver()

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
public fun <T: @Serializable Any> ItemMeta.get(tag: String, clazz: KClass<T>): T? = this.getTag(Tag.NBT(tag))?.let {
    return@let NBTParser.deserialize(clazz.serializer(), it as? NBTCompound ?: return null)
}

public inline fun <reified T: @Serializable Any> ItemMeta.get(tag: String): T? = this.get(tag, T::class)

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
public fun <T: @Serializable Any> ItemMeta.get(tag: String, module: SerializersModule, clazz: KClass<T>): T? = this.getTag(Tag.NBT(tag))?.let {
    return@let NbtFormat(module).deserialize(clazz.serializer(), it as? NBTCompound ?: return null)
}

public inline fun <reified T: @Serializable Any> ItemMeta.get(tag: String, module: SerializersModule): T? = this.get(tag, module, T::class)
@ExperimentalServerStorageAPI
public fun ItemMetaBuilder.serverData(receiver: ItemMetaServerDataProvider.() -> Unit) = ItemMetaServerDataProvider(this).receiver()