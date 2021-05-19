package world.cepi.kstom.data

import net.minestom.server.data.Data
import net.minestom.server.data.DataImpl

inline fun data(data: Data = DataImpl(), dataReceiver: Data.() -> Unit): Data {
    dataReceiver(data)

    return data
}