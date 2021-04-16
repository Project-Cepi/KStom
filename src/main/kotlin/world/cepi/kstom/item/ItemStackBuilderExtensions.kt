package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.minestom.server.item.ItemStack
import net.minestom.server.item.ItemStackBuilder

public var ItemStackBuilder.amount: Int
    get() = 0
    set(value) = run { this.amount(value) }

public var ItemStackBuilder.lore: List<Component>
    get() = emptyList()
    set(value) = run { this.lore(value) }

public var ItemStackBuilder.displayName: Component
    get() = Component.empty()
    set(value) = run { this.displayName(value) }

public fun ItemStack.and(init: ItemStackBuilder.() -> Unit) {
    this.with(init)
}