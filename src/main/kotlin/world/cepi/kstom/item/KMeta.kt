package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder
import net.minestom.server.item.ItemStackBuilder

/**
 * DSL for Meta.
 */
fun ItemStackBuilder.withMeta(init: ItemMetaBuilder.() -> Unit) =
    this.meta {
        it.init()
        return@meta it
    }