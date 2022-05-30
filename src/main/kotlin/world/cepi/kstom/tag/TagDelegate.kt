package world.cepi.kstom.tag

import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagReadable
import net.minestom.server.tag.TagWritable
import kotlin.reflect.KProperty

class TagDelegate<T>(private val tag: Tag<T>) {
    operator fun getValue(thisRef: TagReadable?, property: KProperty<*>): T? {
        return thisRef?.getTag(tag)
    }

    operator fun setValue(thisRef: TagWritable?, property: KProperty<*>, value: T) {
        thisRef?.setTag(tag, value)
    }
}
