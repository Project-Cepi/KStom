package world.cepi.kstom.nbt

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTLong
import org.jglrxavpok.hephaistos.nbt.NBTString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.CollectionClass
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass
import world.cepi.kstom.nbt.classes.PrimitiveClass

class NBTEncoderTests : StringSpec({
    @OptIn(kotlin.time.ExperimentalTime::class)
    "classes containing primitives should be encoded correctly".config(enabled = false) {
        val primitive = PrimitiveClass(5, 6, 5, "\"Hi\"")

        NBTParser.serialize(primitive) shouldBe primitive.createNonAutoNBT()
    }

    "nested classes should be encoded correctly" {
        val data = ComplexClass(5, 4, 2, false, InterestingClass("hey", 'h'))

        NBTParser.serialize(data) shouldBe data.createNonAutoNBT()

        val dataTrue = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))

        NBTParser.serialize(dataTrue) shouldBe dataTrue.createNonAutoNBT()
    }

    "collections in classes should be encoded correctly" {
        val data = CollectionClass(5, 9, 3, listOf(4, 3))

        NBTParser.serialize(data) shouldBe data.createNonAutoNBT()
    }
})