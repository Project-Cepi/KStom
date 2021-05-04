package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.arguments.ArgumentType

fun old() {
    ArgumentType.Literal("hello")
}

fun new() {
    "hello".literal()
}