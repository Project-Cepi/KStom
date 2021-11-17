package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder
import net.minestom.server.item.ItemStack
import net.minestom.server.item.ItemStackBuilder
import net.minestom.server.item.Material

/**
 * DSL for Items.
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
fun item(material: Material = Material.PAPER, amount: Int = 1, init: ItemMetaBuilder.() -> Unit): ItemStack {
    return ItemStack.of(material, amount).withMeta { meta -> init(meta); meta }
}