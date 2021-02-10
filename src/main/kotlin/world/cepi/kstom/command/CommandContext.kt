package world.cepi.kstom.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments

/**
 * CommandContext is being passed down as a receiver
 * when command with syntax is being called
 */
public data class CommandContext<T>(
    val sender: CommandSender,
    val args: T,
    val rawArgs: Arguments
)