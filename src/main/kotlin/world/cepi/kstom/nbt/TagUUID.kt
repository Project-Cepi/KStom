package world.cepi.kstom.nbt

import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagReadable
import net.minestom.server.tag.TagSerializer
import net.minestom.server.tag.TagWritable
import java.util.*

class TagUUID(val name: String) : TagSerializer<UUID> {

    override fun read(reader: TagReadable): UUID? {
        val array = reader.getTag(Tag.LongArray(name)) ?: return null

        return UUID(
            array.getOrNull(0) ?: return null,
            array.getOrNull(1) ?: return null
        )
    }

    override fun write(writer: TagWritable, value: UUID?) {

        if (value == null) return

        writer.setTag(Tag.LongArray(name), LongArray(2).also {
            it[0] = value.leastSignificantBits
            it[1] = value.mostSignificantBits
        })
    }

}