package world.cepi.kstom.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.tag.Tag
import world.cepi.kstom.serializer.MinestomJSON

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> TagJson(name: String) = Tag.String(name).map<T>(
    { jsonString -> MinestomJSON.decodeFromString(jsonString) },
    { jsonEntity -> MinestomJSON.encodeToString(jsonEntity) }
)