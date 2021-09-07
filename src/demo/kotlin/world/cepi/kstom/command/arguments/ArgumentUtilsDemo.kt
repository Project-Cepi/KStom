package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.Manager
import java.util.*

fun old() {
    ArgumentType.Literal("hello")
}

fun new() {
    "hello".literal()

    ArgumentContext { (this as? Player)?.instance }
}