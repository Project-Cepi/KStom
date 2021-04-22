package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTInt
import org.jglrxavpok.hephaistos.nbt.NBTList
import org.jglrxavpok.hephaistos.nbt.NBTTypes

@Serializable
data class CollectionClass(
    val first: Int,
    val second: Byte,
    val third: Short,
    val list: List<Int>
) {
    fun createNonAutoNBT(): NBTCompound {
        val compound = NBTCompound().setInt("first", first).setByte("second", second).setShort("third", third)
        compound["list"] = NBTList<NBTInt>(NBTTypes.TAG_Int).apply {
            list.forEach { add(NBTInt(it)) }
        }
        return compound
    }
}