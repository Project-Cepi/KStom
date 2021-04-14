package world.cepi.kstom.item

import org.jglrxavpok.hephaistos.nbt.NBT

/**
 * Utility class for handling NBT -- works similarly to arguments.
 */
abstract class ItemMetaDataStorage<T>(val id: String) {

    abstract fun write(obj: T)
    abstract fun read(nbt: NBT)

}