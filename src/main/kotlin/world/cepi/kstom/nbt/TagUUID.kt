package world.cepi.kstom.nbt

import net.minestom.server.tag.Tag
import java.util.*
import java.util.function.BiConsumer

class TagUUID(val name: String) : Tag<UUID>(name,
    get@ { compound ->
        val array = compound.getLongArray(name) ?: return@get null

        return@get UUID(
            array.getOrNull(0) ?: return@get null,
            array.getOrNull(1) ?: return@get null
        )
    },
    BiConsumer { compound, value ->
        compound.setLongArray(name, LongArray(2).also {
            it[0] = value.leastSignificantBits
            it[1] = value.mostSignificantBits
        })
    }
)