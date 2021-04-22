package world.cepi.kstom.nbt

import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTLong
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.ComplexClass
import world.cepi.kstom.nbt.classes.InterestingClass
import world.cepi.kstom.nbt.classes.PrimitiveClass

class NBTDecoderTests {

    @Test
    fun `basic primitive classes are decoded correctly`() {
        val primitive = PrimitiveClass(5, 6, 5)

        assertEquals(primitive, decodeFromNBT<PrimitiveClass>(primitive.createNonAutoNBT()))

    }

    @Test
    fun `basic primitives are decoded correctly`() {
        val primitive = 5

        assertEquals(5, decodeFromNBT(NBTInt(5)))
        assertEquals(5L, decodeFromNBT(NBTLong(5L)))

    }

    @Disabled("still need complex types!")
    fun `complex primitive classes are decoded correctly`() {
        val data = ComplexClass(5, 4, 2, InterestingClass("hey", 'h'))

        assertEquals(data, decodeFromNBT<ComplexClass>(data.createNonAutoNBT()))
    }

}