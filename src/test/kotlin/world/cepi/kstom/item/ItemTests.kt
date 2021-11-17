package world.cepi.kstom.item

import io.kotest.core.plan.Descriptor.EngineDescriptor.displayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import net.kyori.adventure.text.Component
import world.cepi.kstom.nbt.classes.CollectionClass
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass

class ItemTests : StringSpec({
    val item = item(amount = 5) {
        lore = listOf(Component.text("Hello!"))
        displayName = Component.text("Hey!")
        damage = 5
        unbreakable = true

        this["complexData"] = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))
        this["complexListData"] = CollectionClass(5, 9, 3, listOf(4, 3))
    }.withAmount(7)

    "item should be mutated" {
        item.amount shouldBe 7
    }

    "complex data should be stored" {
        val data = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))
        val otherData = CollectionClass(5, 9, 3, listOf(4, 3))

        item.meta.get<ComplexClass>("complexData") shouldBe data
        item.meta.get<CollectionClass>("complexListData") shouldBe otherData
    }

    "data should return null if not found" {
        item.meta.get<ComplexClass>("weirdData").shouldBeNull()
    }
})