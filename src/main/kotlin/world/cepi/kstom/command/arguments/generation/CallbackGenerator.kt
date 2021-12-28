package world.cepi.kstom.command.arguments.generation

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.number.ArgumentNumber

object CallbackGenerator {

    fun generateTag(argument: Argument<*>) = when (argument) {
        is ArgumentNumber -> {

            val range = if (argument.min != null || argument.max != null) ": ${
                if (argument.min == null) "" else argument.min
            }..${
                if (argument.max == null) "" else argument.max
            }" else ""

            "${argument.id}${range}"
        }
        else -> argument.id
    }

    var errorSymbol = Component.text("!")

    fun applyCallback(generatedArguments: ArgumentGenerator<*>) {
        generatedArguments.callback = {

            val flattenedArgs = generatedArguments.arguments.flatten()

            sender.sendMessage(
                errorSymbol
                    .append(Component.text(" Wrong input (Yours: ${exception.input})", NamedTextColor.RED))
                    .append(Component.newline())
            )

            sender.sendMessage(flattenedArgs.map {
                Component.text(it::class.simpleName!!.replace("Argument", ""), NamedTextColor.GRAY)
                    .append(Component.text("<${generateTag(it)}>", NamedTextColor.RED))
            }.foldIndexed(Component.empty()) { index, acc, textComponent ->
                acc.append(textComponent).let {
                    if (index + 1 == flattenedArgs.size)
                        it
                    else
                        it.append(Component.newline())
                }
            })
        }
    }

}