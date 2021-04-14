package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder
import net.minestom.server.item.ItemTag

public operator fun <T> ItemMetaBuilder.set(tag: ItemTag<T>, obj: T) {
    this.set(tag, obj)
}