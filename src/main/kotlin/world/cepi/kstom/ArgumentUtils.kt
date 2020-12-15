package world.cepi.kstom

import net.minestom.server.command.builder.arguments.ArgumentWord

fun ArgumentWord.asSubcommand(): ArgumentWord = this.from(this.id)
