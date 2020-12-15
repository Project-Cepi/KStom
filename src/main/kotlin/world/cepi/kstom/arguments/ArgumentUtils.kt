package world.cepi.kstom.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.Player

fun ArgumentWord.asSubcommand(): ArgumentWord = this.from(this.id)

fun Arguments.getPlayer(id: String): Player {

    val username = this.getString(id)
    return MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == username }
}