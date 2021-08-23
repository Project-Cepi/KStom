package world.cepi.kstom.command.arguments

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument
import java.util.*
import java.util.function.Supplier

class ArgumentContextValue<T>(val lambda: CommandSender.() -> T?) {
    fun from(sender: CommandSender) = lambda(sender)
}

class ArgumentContext<T>(
    val lambda: CommandSender.() -> T?
) : Argument<ArgumentContextValue<T>>("context${UUID.randomUUID()}") {

    init {
        setDefaultValue(ArgumentContextValue(lambda))
    }

    override fun parse(input: String): ArgumentContextValue<T> =
        ArgumentContextValue(lambda)

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {}

    override fun toString() = "Context<$id>"

}