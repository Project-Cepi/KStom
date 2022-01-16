package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.builder.arguments.Argument
import world.cepi.kstom.command.kommand.Kommand

interface CustomArgumentGeneration<T> {

    val argumentsForGeneration: List<List<Argument<*>>>

    fun generate(syntax: Kommand.SyntaxContext, args: List<String>, index: Int): T

}