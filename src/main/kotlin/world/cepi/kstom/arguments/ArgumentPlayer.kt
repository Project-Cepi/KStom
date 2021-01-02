package world.cepi.kstom.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.Player

class ArgumentPlayer(id: String) : ArgumentWord(id) {
    override fun from(vararg restrictions: String?): ArgumentWord = this
    override fun getRestrictions(): Array<String> {
        val restrictions: MutableList<String> = mutableListOf()
        MinecraftServer.getConnectionManager().onlinePlayers.forEach { restrictions.add(it.username) }
        return restrictions.toTypedArray()
    }
}

fun Arguments.getPlayer(id: String): Player = MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == this.getString(id) }