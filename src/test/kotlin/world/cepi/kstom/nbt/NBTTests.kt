package world.cepi.kstom.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import world.cepi.kstom.nbt.classes.PrimitiveClass

class NBTTests {

    @ExperimentalSerializationApi
    @InternalSerializationApi
    @Test
    fun `basic primitive classes are encoded correctly`() {
        val primitive = PrimitiveClass(5, 6, 5)

        assertEquals(primitive.createNonAutoNBT(), encodeToNBT(primitive))

    }

    @ExperimentalSerializationApi
    @InternalSerializationApi
    @Test
    fun `basic primitives are encoded correctly`() {
        val primitive = 5

        assertEquals(NBTInt(5), encodeToNBT(5))

    }

}