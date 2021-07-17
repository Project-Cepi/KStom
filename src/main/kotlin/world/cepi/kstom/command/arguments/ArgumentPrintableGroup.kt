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

class ArgumentPrintableGroup(id: String, private val group: List<Argument<*>>) :
    Argument<Pair<String, CommandContext>>(id, true, false), List<Argument<*>> {

    constructor(id: String, group: Array<out Argument<*>>) : this(id, group.toList())

    override val size: Int
        get() = group.size

    @Throws(ArgumentSyntaxException::class)
    override fun parse(input: String): Pair<String, CommandContext> {
        val validSyntaxes: List<ValidSyntaxHolder> = ArrayList()
        CommandParser.parse(
            null,
            group.toTypedArray(),
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

        if (!group.toTypedArray().contentEquals(other.group.toTypedArray())) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + group.toTypedArray().contentHashCode()
        return result
    }

    override fun toString() = group.toTypedArray().contentDeepToString()

    companion object {
        const val INVALID_ARGUMENTS_ERROR = 1
    }

    override fun contains(element: Argument<*>) = group.contains(element)
    override fun containsAll(elements: Collection<Argument<*>>) = group.containsAll(elements)
    override fun isEmpty() = group.isEmpty()
    override fun iterator() = group.iterator()
    override fun get(index: Int) = group.get(index)
    override fun indexOf(element: Argument<*>) = group.indexOf(element)
    override fun lastIndexOf(element: Argument<*>) = group.lastIndexOf(element)
    override fun listIterator(): ListIterator<Argument<*>> = group.listIterator()
    override fun listIterator(index: Int): ListIterator<Argument<*>> = group.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<Argument<*>> = group.subList(fromIndex, toIndex)
}
