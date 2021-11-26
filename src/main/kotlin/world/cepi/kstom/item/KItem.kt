package world.cepi.kstom.item

import net.minestom.server.item.*

/**
 * DSL for Items.
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
fun item(material: Material = Material.PAPER, amount: Int = 1, init: ItemMetaBuilder.() -> Unit = {}): ItemStack {
    return ItemStack.of(material, amount).withMeta { metaBuilder: ItemMetaBuilder -> init(metaBuilder); metaBuilder }
}