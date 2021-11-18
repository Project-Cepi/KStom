package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializable
data class PrimitiveClass(
    val first: Int,
    val second: Byte,
    val third: Short,
    val fourth: String
) {
    fun createNonAutoNBT(): NBTCompound {
        val compound = NBTCompound()
        compound.setInt("first", first)
        compound.setInt("second", second.toInt())
        compound.setInt("third", third.toInt())
        compound.setString("fourth", fourth)
        return compound
    }
}