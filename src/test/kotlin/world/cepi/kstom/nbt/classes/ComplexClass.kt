package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.*

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
    val boolean: Boolean,
    val interesting: InterestingClass
) {
    fun createNonAutoNBT() = NBT.Kompound {
        this["first"] = NBTInt(first)
        this["second"] = NBTInt(second.toInt())
        this["third"] = NBTInt(third.toInt())
        this["boolean"] = NBTByte(if (boolean) 1 else 0)
        this["interesting"] = NBT.Kompound {
            this["woo"] = NBTString(interesting.woo)
            this["ooo"] = NBTString(interesting.ooo.toString())
        }
    }
}