package world.cepi.kstom.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Player
import net.minestom.server.entity.fakeplayer.FakePlayer
import java.util.*
import kotlin.jvm.Throws

class ArgumentPlayer(id: String) : Argument<Player>(id, false, false) {

    @Throws(ArgumentSyntaxException::class)
    override fun parse(value: String): Player {
        try {
            return MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == value }
        } catch (e: Exception) {
            throw ArgumentSyntaxException("The player does not exist!", value, 1)
        }
    }
}

fun Arguments.getPlayer(id: String): Player = MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == this.getString(id) }