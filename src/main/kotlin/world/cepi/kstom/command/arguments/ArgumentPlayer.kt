package world.cepi.kstom.command.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Player
import net.minestom.server.utils.binary.BinaryWriter
import java.util.function.Consumer

public class ArgumentPlayer(id: String) : Argument<Player>(id, false, false) {

    @Throws(ArgumentSyntaxException::class)
    override fun parse(value: String): Player {
        try {
            return MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == value }
        } catch (e: Exception) {
            throw ArgumentSyntaxException("The player does not exist!", value, 1)
        }
    }

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {
        val argumentNode = simpleArgumentNode(this, executable, false, true)

        argumentNode.parser = "brigadier:string"
        argumentNode.properties = Consumer { packetWriter: BinaryWriter ->
            packetWriter.writeVarInt(0) // Single word
        }
        argumentNode.suggestionsType = SuggestionType.ASK_SERVER.identifier

        nodeMaker.addNodes(arrayOf(argumentNode))
    }
}