package world.cepi.kstom.item

import net.minestom.server.item.*

/**
 * DSL for Items with special meta.
 *
 * @param T The type of the item meta builder
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
fun <T : ItemMetaBuilder> item(material: Material = Material.PAPER, amount: Int = 1, init: T.() -> Unit = {}): ItemStack {
    return ItemStack.of(material, amount).withMeta { metaBuilder: T -> init(metaBuilder); metaBuilder }
}

/**
 * DSL for Items.
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
@JvmName("itemDefault") // Doesn't matter because this library won't be used from Java
fun item(material: Material = Material.PAPER, amount: Int = 1, init: ItemMetaBuilder.() -> Unit = {}): ItemStack =
    item<ItemMetaBuilder>(material, amount, init)