package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket
import net.minestom.server.utils.binary.BinaryWriter
import org.apache.commons.lang3.StringUtils
import java.util.function.Consumer

/** ArgumentEnum that can hold a list of Enums. Meant to transfer the properties of other enum data towards a handler. */
public class ArgumentEnum<T: Enum<T>>(id: String)
: Argument<T>(id, false, false) {

    public var enumArray: List<T> = listOf()

    public companion object {
        public const val SPACE_ERROR: Int = 1
        public const val RESTRICTION_ERROR: Int = 2
    }

    public fun from(vararg enums: T): ArgumentEnum<T> {
        this.enumArray = enums.toList()
        return this
    }

    override fun parse(input: String): T {
        if (StringUtils.SPACE in input) throw ArgumentSyntaxException(
            "Word cannot contain space character",
            input,
            SPACE_ERROR
        )

        return enumArray.firstOrNull { it.name.equals(input, true) }
            ?: throw ArgumentSyntaxException(
                "Word needs to be in the restriction list",
                input,
                RESTRICTION_ERROR
            )
    }

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {
        // Add the single word properties + parser
        val wordConsumer =
            Consumer { node: DeclareCommandsPacket.Node ->
                node.parser = "brigadier:string"
                node.properties = Consumer { packetWriter: BinaryWriter ->
                    packetWriter.writeVarInt(0) // Single word
                }
            }


        // Create a primitive array for mapping
        val nodes = arrayOfNulls<DeclareCommandsPacket.Node>(this.enumArray.size)

        // Create a node for each restrictions as literal
        for (i in nodes.indices) {
            val argumentNode = DeclareCommandsPacket.Node()
            argumentNode.flags = DeclareCommandsPacket.getFlag(
                DeclareCommandsPacket.NodeType.LITERAL,
                executable, false, false
            )
            argumentNode.name = this.enumArray.get(i).name.toLowerCase()
            wordConsumer.accept(argumentNode)
            nodes[i] = argumentNode
        }
        nodeMaker.addNodes(nodes)
    }
}