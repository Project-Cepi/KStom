package world.cepi.kstom.data

import net.minestom.server.data.Data
import net.minestom.server.data.DataImpl

fun old(): Data {
    val data: Data = DataImpl()

    data.set("test", 1)
    data.set("woo", 3)

    return data
}

fun new(): Data = data {
    this["test"] = 1
    this["woo"] = 3
}