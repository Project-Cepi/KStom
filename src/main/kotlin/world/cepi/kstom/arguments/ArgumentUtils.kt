package world.cepi.kstom.arguments

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.Player

fun ArgumentWord.asSubcommand(): ArgumentWord = this.from(this.id)
fun String.asSubcommand(): ArgumentWord = ArgumentType.Word(this).from(this)

fun <T> Arguments.get(id: String) = this.getObject(id) as? T