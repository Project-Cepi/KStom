package world.cepi.kstom.nbt

import org.jglrxavpok.hephaistos.nbt.*
import world.cepi.kstom.nbt.compound.decodeFromCompoundNBT

inline fun <reified T> decodeFromNBT(nbt: NBT): T? {
    return when (nbt::class) {
        NBTInt::class -> (nbt as NBTInt).value as T
        NBTString::class -> (nbt as NBTString).value as T
        NBTLong::class -> (nbt as NBTLong).value as T
        NBTCompound::class -> {
            return try {
                decodeFromCompoundNBT<T>(nbt as NBTCompound)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        else -> null
    }
}