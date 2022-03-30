package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minestom.server.item.ItemMetaBuilder
import net.minestom.server.item.ItemStack

fun ItemMetaBuilder.lore(tagResolver: TagResolver = TagResolver.empty(), builder: KLore.() -> Unit) {
    this.lore = KLore().apply(builder).list.toList()
}

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

fun ItemStack.and(amount: Int? = null, init: ItemMetaBuilder.() -> Unit) = this.with {
    if (amount != null) it.amount(amount)
    it.meta(meta.with(init))
}