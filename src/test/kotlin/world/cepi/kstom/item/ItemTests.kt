package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.CollectionClass
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass
import java.util.*

object ItemTests {

    val item = item {
        amount = 5
        lore = listOf(Component.text("Hello!"))
        displayName = Component.text("Hey!")

        withMeta {
            damage = 5
            unbreakable = true

            clientData {
                this["complexData"] = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))
                this["complexListData"] = CollectionClass(5, 9, 3, listOf(4, 3))
            }

            serverData {
                this["someOtherData"] = UUID(5L, 5L)
            }
        }

    }.and {
        amount = 7
    }

    @Test
    fun `check amount is mutated`() {
        assertEquals(item.amount, 7)
    }

    @Test
    fun `ensure complex data is translated`() {
        val data = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))
        val otherData = CollectionClass(5, 9, 3, listOf(4, 3))

        assertEquals(data, item.meta.get<ComplexClass>("complexData"))
        assertEquals(otherData, item.meta.get<CollectionClass>("complexListData"))
    }

    @Test
    fun `return null if NBT is not found`() {
        assertEquals(item.meta.get<ComplexClass>("weirdData"), null)
    }

}