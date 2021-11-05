package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.arguments.ArgumentType

fun ArgumentByte(id: String) = ArgumentType.Integer(id)
    .min(Byte.MIN_VALUE.toInt())
    .max(Byte.MAX_VALUE.toInt())
    .map { it.toByte() }