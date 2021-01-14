package world.cepi.kstom.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.Player
import net.minestom.server.entity.fakeplayer.FakePlayer
import java.util.*

class ArgumentPlayer(id: String) : Argument<Player>(id) {
    override fun getCorrectionResult(value: String): Int {
        return if (value.contains(" ")) ArgumentWord.SPACE_ERROR else SUCCESS
    }

    override fun parse(value: String): Player {
        return MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == value }
    }

    fun getConditionResult(value: String): Int {
        return if (MinecraftServer.getConnectionManager().onlinePlayers.any { it.username == value })
            SUCCESS
        else
            ArgumentWord.RESTRICTION_ERROR
    }

    override fun getConditionResult(player: Player): Int {
        return 0
    }
}

fun Arguments.getPlayer(id: String): Player = MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == this.getString(id) }