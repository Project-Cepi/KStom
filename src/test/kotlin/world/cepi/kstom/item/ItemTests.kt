package world.cepi.kstom

import net.kyori.adventure.text.Component
import net.minestom.server.item.ItemTag
import world.cepi.kstom.item.*

val item = item {
    amount = 5
    lore = listOf(Component.text("Hey!"))
    displayName = Component.text("Hey!")

    withMeta {
        damage = 5
        unbreakable = true

        this[ItemTag.Integer("test")] = 1
    }


}