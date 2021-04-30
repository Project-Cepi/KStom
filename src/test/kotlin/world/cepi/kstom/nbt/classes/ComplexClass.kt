package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.NBTCompound

@Serializable
data class InterestingClass(
    val woo: String,
    val ooo: Char
)

@Serializable
data class ComplexClass(
    val first: Int,
    val second: Byte,
    val third: Short,
    val interesting: InterestingClass
) {
    fun createNonAutoNBT(): NBTCompound {
        val compound = NBTCompound().setInt("first", first).setInt("second", second.toInt()).setInt("third", third.toInt())
        compound["interesting"] = NBTCompound().setString("woo", interesting.woo).setString("ooo", interesting.ooo.toString())
        return compound
    }
}