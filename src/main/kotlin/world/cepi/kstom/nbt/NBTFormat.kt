package world.cepi.kstom.nbt

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.contextual
import net.minestom.server.instance.block.Block
import org.jglrxavpok.hephaistos.nbt.*
import world.cepi.kstom.serializer.*
import java.io.StringReader

/**
 * Objects to and from NBT!
 *
 * @author CmdrNorthpaw
 */
@OptIn(ExperimentalSerializationApi::class)
open class NBTFormat(module: SerializersModule = EmptySerializersModule) {

    val json = Json {
        serializersModule = module
        ignoreUnknownKeys = true
        repeat()
    }

    /**
     * Converts [obj] into a [NBTCompound] that represents [obj].
     * Later [deserialize] can be called to retrieve an identical instance of [obj] from the [NBTCompound].
     *
     * These functions are not documented because I think they would be confusing.
     * Do you want these to be an official part of the API? Please make an issue.
     */
    fun <T> serialize(serializer: SerializationStrategy<T>, obj: T): NBTCompound {
        return SNBTParser(
            StringReader(json.encodeToString(serializer, obj))
        ).parse() as NBTCompound
    }

    inline fun <reified T> serialize(obj: T): NBTCompound {
        return serialize(serializer(), obj)
    }

    fun <T> deserialize(deserializer: DeserializationStrategy<T>, tag: NBTCompound): T {
        return json.decodeFromString(
            deserializer, tag.toSNBT()
                .replace("1B", "true")
                .replace("0B", "false")
        )
    }

    inline fun <reified T> deserialize(tag: NBTCompound): T {
        return deserialize(serializer(), tag)
    }

}

object NBTParser : NBTFormat(MinestomSerializableModule())