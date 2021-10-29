package world.cepi.kstom.util

import net.minestom.server.utils.mojang.MojangUtils
import java.math.BigInteger
import java.util.*

object Mojang {
    fun getUuid(username: String): UUID {
        val jsonObject = MojangUtils.fromUsername(username) ?: return UUID.randomUUID()
        val id = jsonObject.get("id")?.asString ?: return UUID.randomUUID()
        val bigInteger = BigInteger(id, 16)
        return UUID(bigInteger.shiftRight(64).toLong(), bigInteger.toLong())
    }
}