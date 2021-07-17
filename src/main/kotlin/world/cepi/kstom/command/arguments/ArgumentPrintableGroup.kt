package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.command.builder.parser.CommandParser
import net.minestom.server.command.builder.parser.ValidSyntaxHolder
import net.minestom.server.utils.StringUtils
import java.util.*
import kotlin.reflect.KClass

class ArgumentPrintableGroup(clazz: KClass<*>, val group: Array<out Argument<*>>) :
    Argument<Pair<String, CommandContext>>(clazz.qualifiedName!!, true, false) {

    @Throws(ArgumentSyntaxException::class)
    override fun parse(input: String): Pair<String, CommandContext> {
        val validSyntaxes: List<ValidSyntaxHolder> = ArrayList()
        CommandParser.parse(
            null,
            group,
            input.split(StringUtils.SPACE.toRegex()).toTypedArray(),
            input,
            validSyntaxes,
            null
        )
        val context = CommandContext(input)
        CommandParser.findMostCorrectSyntax(validSyntaxes, context)
        if (validSyntaxes.isEmpty()) {
            throw ArgumentSyntaxException("Invalid arguments", input, INVALID_ARGUMENTS_ERROR)
        }
        return id to context
    }

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {
        for (i in group.indices) {
            val isLast = i == group.size - 1
            group[i].processNodes(nodeMaker, executable && isLast)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false

        if (this === other) return true

        if (other !is ArgumentPrintableGroup) return false

        if (!group.contentEquals(other.group)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + group.contentHashCode()
        return result
    }

    override fun toString() = group.contentDeepToString()

    companion object {
        const val INVALID_ARGUMENTS_ERROR = 1
    }
}
