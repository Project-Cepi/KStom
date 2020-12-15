package world.cepi.kstom.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.arguments.ArgumentDynamicWord
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType

class ArgumentPlayer(val argumentID: String): ArgumentDynamicWord(argumentID, SuggestionType.ASK_SERVER) {

    init {
        this.fromRestrictions { name ->
            MinecraftServer.getConnectionManager().onlinePlayers.any { it.username == name }
        }
    }

}

fun ArgumentType.Player(id: String): ArgumentPlayer = ArgumentPlayer(id)