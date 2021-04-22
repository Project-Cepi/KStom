package world.cepi.kstom.nbt.list

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTList
import org.jglrxavpok.hephaistos.nbt.NBTTypes
import world.cepi.kstom.nbt.encodeToNBT
import java.lang.IllegalArgumentException

@ExperimentalSerializationApi
class NBTListEncoder<T: NBT>(nbtClass: Class<T>) : AbstractEncoder() {

    val nbt = NBTList<NBT>(NBTTypes.getID(nbtClass))

    override val serializersModule: SerializersModule = EmptySerializersModule

    override fun encodeValue(value: Any) {
        val valueNBT = encodeToNBT(value) ?: throw IllegalArgumentException("Requires NBT serializable!")
        if (valueNBT.ID != nbt.subtagType) throw IllegalArgumentException("Requires proper subtype!")

        nbt.add(valueNBT)
    }

}