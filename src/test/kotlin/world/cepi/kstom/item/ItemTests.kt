package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass

object ItemTests {

    val item = item {
        amount = 5
        lore = listOf(Component.text("Hello!"))
        displayName = Component.text("Hey!")

        withMeta {
            damage = 5
            unbreakable = true

//            clientData {
//                this["myCoolData"] = 50
//            }

            serverData {
                this["someOtherData"] = ComplexClass(5, 4, 2, InterestingClass("hey", 'h'))
            }
        }

    }.and {
        amount = 7
    }

    @Test
    fun `check amount is mutated`() {
        assertEquals(item.amount, 7)
    }

}