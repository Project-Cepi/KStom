package world.cepi.kstom.item

import net.minestom.server.item.*

/**
 * DSL for Meta.
 */
fun ItemStackBuilder.withMeta(init: ItemMetaBuilder.() -> Unit) =
    this.meta { it: ItemMetaBuilder ->
        it.init()
        return@meta it
    }