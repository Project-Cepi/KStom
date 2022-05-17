package world.cepi.kstom.item

import net.minestom.server.item.*

/**
 * DSL for Meta of specific type.
 */
inline fun <reified V : ItemMetaView.Builder, reified T : ItemMetaView<V>> ItemStack.Builder.withMeta(noinline init: V.() -> Unit) =
    this.meta(T::class.java, init)

/**
 * DSL for Meta.
 */
@JvmName("withMetaDefault") // Doesn't matter because this library won't be used from Java
fun ItemStack.Builder.withMeta(init: ItemMeta.Builder.() -> Unit) =
    this.meta { init(it); it }