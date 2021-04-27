package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import net.minestom.server.item.ItemMeta
import net.minestom.server.item.ItemTag
import world.cepi.kstom.nbt.NBTParser
import world.cepi.kstom.nbt.NbtFormat

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.TYPEALIAS)
@RequiresOptIn("Literally doesn't garbage collect at all.", level = RequiresOptIn.Level.WARNING)
public annotation class ExperimentalServerStorageAPI

class ItemMetaClientData(val metaBuilder: ItemMetaBuilder) {

    inline operator fun <reified T: @Serializable Any> set(tag: String, item: @Serializable T) {
        metaBuilder.set(ItemTag.NBT(tag), NBTParser.serialize(item))
    }

    inline operator fun <reified T: @Serializable Any> set(tag: String, module: SerializersModule, item: @Serializable T) {
        metaBuilder.set(ItemTag.NBT(tag), NbtFormat(module).serialize(item))
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

public inline fun <reified T: @Serializable Any> ItemMeta.get(tag: String): T? = this.get(ItemTag.NBT(tag))?.let {
    return@let NBTParser.deserialize<T>(it)
}

public inline fun <reified T: @Serializable Any> ItemMeta.get(tag: String, module: SerializersModule): T? = this.get(ItemTag.NBT(tag))?.let {
    return@let NbtFormat(module).deserialize<T>(it)
}

@ExperimentalServerStorageAPI
public fun ItemMetaBuilder.serverData(receiver: ItemMetaServerDataProvider.() -> Unit) = ItemMetaServerDataProvider(this).receiver()