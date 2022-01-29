package world.cepi.kstom.nbt

import net.minestom.server.tag.Tag
import org.jglrxavpok.hephaistos.nbt.NBTLongArray
import java.util.*

fun TagUUID(name: String) = Tag.NBT<NBTLongArray>(name).map<UUID>(
    get@ { map ->

        if (map.size != 2) return@get null

        return@get UUID(
            map[0],
            map[1]
        )
    },
    set@ { value ->
        NBTLongArray(
            value?.mostSignificantBits ?: return@set null,
            value.leastSignificantBits
        )
    }
)