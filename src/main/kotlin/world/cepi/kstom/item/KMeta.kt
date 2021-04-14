package world.cepi.kstom.item

import net.minestom.server.item.*

/**
 * DSL for Meta.
 */
public fun ItemStackBuilder.withMeta(init: ItemMetaBuilder.() -> Unit) =
    this.meta {
        it.init()
        return@meta it
    }