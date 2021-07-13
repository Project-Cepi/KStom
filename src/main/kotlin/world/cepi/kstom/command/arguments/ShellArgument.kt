package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument

object ShellArgument : Argument<Unit>("shell") {
    override fun parse(input: String) {

    }

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {

    }

    override fun toString() = "Shell"
}