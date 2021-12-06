package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import world.cepi.kstom.command.arguments.suggest

interface DynamicWordGenerator {

    fun grab(sender: CommandSender): List<SuggestionEntry> =
        grabSimple(sender).map { SuggestionEntry(it) }

    fun grabSimple(sender: CommandSender): List<String>

}