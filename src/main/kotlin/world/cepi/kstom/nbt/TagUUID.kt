package world.cepi.kstom.nbt

import net.minestom.server.tag.Tag
import java.util.*

fun TagUUID(name: String) = Tag.LongArray(name).map<UUID>(
    get@ { map ->
        return@get UUID(
            map.getOrNull(0) ?: return@get null,
            map.getOrNull(1) ?: return@get null
        )
    },
    set@ { value ->
        LongArray(2).also {
            it[0] = value?.mostSignificantBits ?: return@set null
            it[1] = value.leastSignificantBits
        }
    }
)