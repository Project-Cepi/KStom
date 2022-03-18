package world.cepi.kstom.nbt

import net.minestom.server.tag.Tag

fun TagBoolean(id: String) = Tag.Byte(id).map({
    return@map it == 1.toByte()
}, {
    return@map if (it) 1.toByte() else 0.toByte()
})