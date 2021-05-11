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

class NBTDecoderTests {

    @Test
    fun `basic primitive classes are decoded correctly`() {
        val primitive = PrimitiveClass(5, 6, 5)

        assertEquals(primitive, NBTParser.deserialize<PrimitiveClass>(primitive.createNonAutoNBT()))

    }

    @Test
    fun `complex primitive classes are decoded correctly`() {
        val data = ComplexClass(5, 4, 2, true, InterestingClass("hey", 'h'))

        assertEquals(data, NBTParser.deserialize<ComplexClass>(data.createNonAutoNBT()))
    }

    @Test
    fun `complex primitive classes are decoded with false boolean correctly`() {
        val data = ComplexClass(5, 4, 2, false, InterestingClass("hey", 'h'))

        assertEquals(data, NBTParser.deserialize<ComplexClass>(data.createNonAutoNBT()))
    }

    @Test
    fun `collections are encoded correctly`() {
        val data = CollectionClass(5, 9, 3, listOf(4, 3))
        assertEquals(data, NBTParser.deserialize<CollectionClass>(data.createNonAutoNBT()))
    }

}