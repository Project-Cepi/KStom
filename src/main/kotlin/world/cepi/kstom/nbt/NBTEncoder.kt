package world.cepi.kstom.nbt

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.serializer
import org.jglrxavpok.hephaistos.nbt.*
import world.cepi.kstom.nbt.compound.encodeToCompoundNBT

inline fun <reified T> encodeToNBT(value: T): NBT? {

    return when (serializer<T>()) {
        Int.serializer() -> NBTInt(value as Int)
        Double.serializer() -> NBTDouble(value as Double)
        Long.serializer() -> NBTLong(value as Long)
        String.serializer() -> NBTString(value as String)
        else -> {
            return try {
                encodeToCompoundNBT(value)
            } catch (e: Exception) {
                null
            }
        }
    }
}