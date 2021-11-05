package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player

fun old() {
    ArgumentType.Literal("hello")
}

fun new() {
    "hello".literal()

    ArgumentContext { (this as? Player)?.instance }
}