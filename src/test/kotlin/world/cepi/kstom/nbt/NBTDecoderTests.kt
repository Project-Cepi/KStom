package world.cepi.kstom.nbt

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTLong
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.CollectionClass
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass
import world.cepi.kstom.nbt.classes.PrimitiveClass

class NBTDecoderTests : StringSpec({
    "primitive classes are decoded correctly" {
        val primitive = PrimitiveClass(5, 6, 5, "\"Hi\"")

        NBTParser.deserialize<PrimitiveClass>(primitive.createNonAutoNBT()) shouldBe primitive
    }

    "nested classes are decoded correctly" {
        val data = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))
        val falseData = ComplexClass(5, 4, 2, false, InterestingClass("hey", 'h'))

        NBTParser.deserialize<ComplexClass>(data.createNonAutoNBT()) shouldBe data
        NBTParser.deserialize<ComplexClass>(falseData.createNonAutoNBT()) shouldBe falseData
    }

    "collections are decoded correctly" {
        val data = CollectionClass(5, 9, 3, listOf(4, 3))
        NBTParser.deserialize<CollectionClass>(data.createNonAutoNBT()) shouldBe data
    }
})