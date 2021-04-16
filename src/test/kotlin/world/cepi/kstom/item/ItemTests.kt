package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.minestom.server.item.ItemStack
import net.minestom.server.item.ItemTag
import net.minestom.server.item.Material
import world.cepi.kstom.item.*
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass

val item = item {
    amount = 5
    lore = listOf(Component.text("Hey!"))
    displayName = Component.text("Hey!")

    withMeta {
        damage = 5
        unbreakable = true

        clientData {
            this["myCoolData"] = 50
        }

        serverData {
            this["someOtherData"] = ComplexClass(5, 4, 2, InterestingClass("hey", 'h'))
        }
    }

}