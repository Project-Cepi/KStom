package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.arguments.ArgumentWord
import world.cepi.kstom.Manager

fun ArgumentPlayer(id: String) = ArgumentWord(id)
    .map { Manager.connection.getPlayer(id) }
    .suggest { Manager.connection.onlinePlayers.map { it.username } }