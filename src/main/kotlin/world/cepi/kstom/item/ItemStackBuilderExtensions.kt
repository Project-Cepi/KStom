package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minestom.server.item.ItemMeta
import net.minestom.server.item.ItemStack

fun ItemMeta.Builder.lore(tagResolver: TagResolver = TagResolver.empty(), builder: KLore.() -> Unit) {
    this.lore = KLore().apply(builder).list.toList()
}

var ItemMeta.Builder.lore: List<Component>
    get() = emptyList()
    set(value) = run { this.lore(value) }

var ItemMeta.Builder.displayName: Component
    get() = Component.empty()
    set(value) = run { this.displayName(value) }

var ItemMeta.Builder.damage: Int
    get() = 0
    set(value) = run { this.damage(value) }

var ItemMeta.Builder.unbreakable: Boolean
    get() = false
    set(value) = run { this.unbreakable(value) }

fun ItemStack.and(amount: Int = -1, init: ItemMeta.Builder.() -> Unit) = this.with {
    if (amount != -1) it.amount(amount)
    it.meta(meta.with(init))
}