package world.cepi.kstom.item

import net.minestom.server.item.*

/**
 * DSL for Meta of specific type.
 */
fun <T : ItemMetaBuilder> ItemStackBuilder.withMeta(init: T.() -> Unit) =
    this.meta { it: T ->
        it.init()
        return@meta it
    }

/**
 * DSL for Meta.
 */
@JvmName("withMetaDefault") // Doesn't matter because this library won't be used from Java
fun ItemStackBuilder.withMeta(init: ItemMetaBuilder.() -> Unit) =
    this.withMeta<ItemMetaBuilder>(init)