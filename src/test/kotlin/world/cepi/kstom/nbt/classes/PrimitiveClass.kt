package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializable
data class PrimitiveClass(
    val first: Int,
    val second: Byte,
    val third: Short
) {
    fun createNonAutoNBT(): NBTCompound {
        val compound = NBTCompound()
        compound.setInt("first", first)
        compound.setByte("second", second)
        compound.setShort("third", third)
        return compound
    }
}