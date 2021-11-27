package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.minestom.server.item.ItemMeta
import net.minestom.server.item.ItemMetaBuilder
import net.minestom.server.item.ItemStack
import net.minestom.server.item.ItemStackBuilder

var ItemMetaBuilder.lore: List<Component>
    get() = emptyList()
    set(value) = run { this.lore(value) }

var ItemMetaBuilder.displayName: Component
    get() = Component.empty()
    set(value) = run { this.displayName(value) }

var ItemMetaBuilder.damage: Int
    get() = 0
    set(value) = run { this.damage(value) }

var ItemMetaBuilder.unbreakable: Boolean
    get() = false
    set(value) = run { this.unbreakable(value) }

fun ItemStack.and(amount: Int = -1, init: ItemMetaBuilder.() -> Unit) = this.with {
    if (amount != -1) it.amount(amount)
    it.meta(meta.with(init))
}