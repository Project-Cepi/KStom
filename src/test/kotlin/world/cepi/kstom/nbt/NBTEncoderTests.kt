package world.cepi.kstom.nbt

import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTLong
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.CollectionClass
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass
import world.cepi.kstom.nbt.classes.PrimitiveClass

class NBTEncoderTests {

    @Test
    fun `basic primitive classes are encoded correctly`() {
        val primitive = PrimitiveClass(5, 6, 5)

        assertEquals(primitive.createNonAutoNBT(), NBTParser.serialize(primitive))

    }

    @Disabled("They arent!")
    fun `basic primitives are encoded correctly`() {
        assertEquals(NBTInt(5), NBTParser.serialize(5))
        assertEquals(NBTLong(5L), NBTParser.serialize(5L))

    }

    @Test
    fun `complex primitive classes are encoded correctly`() {
        val data = ComplexClass(5, 4, 2, false, InterestingClass("hey", 'h'))

        assertEquals(data.createNonAutoNBT(), NBTParser.serialize(data))
    }

    @Test
    fun `complex primitive classes are encoded correctly with true boolean`() {
        val data = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))

        assertEquals(data.createNonAutoNBT(), NBTParser.serialize(data))
    }

    @Test
    fun `collections are encoded correctly`() {
        val data = CollectionClass(5, 9, 3, listOf(4, 3))
        assertEquals(data.createNonAutoNBT(), NBTParser.serialize(data))
    }

}