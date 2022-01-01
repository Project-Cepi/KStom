package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTString

@Serializable
data class PrimitiveClass(
    val first: Int,
    val second: Byte,
    val third: Short,
    val fourth: String
) {
    fun createNonAutoNBT() = NBT.Kompound {
        this["first"] = NBTInt(first)
        this["second"] = NBTInt(second.toInt())
        this["third"] = NBTInt(third.toInt())
        this["fourth"] = NBTString(fourth)
    }
}