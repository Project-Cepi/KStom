package world.cepi.kstom.item

import net.minestom.server.item.*
import kotlin.reflect.KClass

/**
 * DSL for Items with special meta.
 *
 * @param T The type of the item meta builder
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
inline fun <V : ItemMetaView.Builder, reified T : ItemMetaView<V>> item(material: Material = Material.PAPER, amount: Int = 1, noinline init: V.() -> Unit = {}): ItemStack =
    item(material, amount, T::class, init)

/**
 * DSL for Items with special meta. Unlike the other method it swaps out generics for a KClass
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param builder The kclass of the builder to use
 * @param init The DSL lambda
 */
@JvmName("itemNonReified")
fun <V : ItemMetaView.Builder, T : ItemMetaView<V>> item(material: Material = Material.PAPER, amount: Int = 1, builder: KClass<T>, init: V.() -> Unit): ItemStack =
    ItemStack.builder(material).amount(amount).meta(builder.java) { init(it) }.build()

/**
 * DSL for Items.
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
@JvmName("itemDefault")
fun item(material: Material = Material.PAPER, amount: Int = 1, init: ItemMeta.Builder.() -> Unit = {}): ItemStack =
    ItemStack.builder(material).amount(amount).meta { it.init(); it }.build()