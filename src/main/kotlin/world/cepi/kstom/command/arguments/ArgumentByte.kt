package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.minecraft.ArgumentItemStack
import net.minestom.server.command.builder.arguments.number.ArgumentNumber
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.item.Material
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket
import net.minestom.server.utils.binary.BinaryWriter

class ArgumentByte(id: String) : ArgumentNumber<Byte>(id) {

    @Throws(ArgumentSyntaxException::class)
    override fun parse(input: String): Byte {
        return try {
            val value = parseValue(input).toByte(getRadix(input))

            // Check range
            if (hasMin && value < min) {
                throw ArgumentSyntaxException("Input is lower than the minimum required value", input, RANGE_ERROR)
            }
            if (hasMax && value > max) {
                throw ArgumentSyntaxException("Input is higher than the minimum required value", input, RANGE_ERROR)
            }
            value
        } catch (e: NumberFormatException) {
            throw ArgumentSyntaxException("Input is not a byte", input, NOT_NUMBER_ERROR)
        } catch (e: NullPointerException) {
            throw ArgumentSyntaxException("Input is not a byte", input, NOT_NUMBER_ERROR)
        }
    }

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {
        val argumentNode = simpleArgumentNode(this, executable, false, false)
        argumentNode.parser = "brigadier:integer"
        argumentNode.properties = BinaryWriter.makeArray { packetWriter: BinaryWriter ->
            packetWriter.writeByte(numberProperties)
            if (hasMin()) packetWriter.writeInt(getMin().toInt())
            if (hasMax()) packetWriter.writeInt(getMax().toInt())
        }
        nodeMaker.addNodes(arrayOf(argumentNode))
    }

    override fun toString() = "Byte<$id>"


}