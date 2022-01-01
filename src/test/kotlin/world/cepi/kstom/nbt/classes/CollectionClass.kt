package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTType.Companion.TAG_Int

@Serializable
data class CollectionClass(
    val first: Int,
    val second: Byte,
    val third: Short,
    val list: List<Int>
) {
    fun createNonAutoNBT() = NBT.Kompound {
        this["first"] = NBTInt(first)
        this["second"] = NBTInt(second.toInt())
        this["third"] = NBTInt(third.toInt())
        this["list"] = NBT.List(TAG_Int, list.map { NBTInt(it) })
    }
}