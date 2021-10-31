package world.cepi.kstom.item

import net.minestom.server.item.ItemMetaBuilder

var ItemMetaBuilder.damage: Int
    get() = 0
    set(value) = run { this.damage(value) }

var ItemMetaBuilder.unbreakable: Boolean
    get() = false
    set(value) = run { this.unbreakable(value) }