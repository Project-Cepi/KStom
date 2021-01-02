package world.cepi.kstom.arguments

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord

/**
 * Automatically generates an ArgumentWord based on the ID
 *
 * @return an ArgumentWord based on the ID
 */
fun ArgumentWord.asSubcommand(): ArgumentWord = this.from(this.id)

/**
 * Automatically generates an ArgumentWord based on the String being passed
 *
 * @return an ArgumentWord based on the String being passed
 */
fun String.asSubcommand(): ArgumentWord = ArgumentType.Word(this).from(this)