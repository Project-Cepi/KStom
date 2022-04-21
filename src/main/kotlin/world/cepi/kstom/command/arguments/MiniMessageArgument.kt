package world.cepi.kstom.command.arguments

import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import world.cepi.kstom.adventure.formatMini

object MiniMessageArgument {
    private val contentRegex = Regex("(?<=<)[A-z1-9#:]+\$")

    private val allMiniMessageTerms = listOf(
        "strikethrough",
        "obfuscated",
        "italic",
        "bold",
        "reset",
        "click",
        "hover",
        "key",
        "insertion",
        "tag",
        "pre",
        "rainbow",
        "gradient",
        "font"
    )

    val vararg = ArgumentType.StringArray("message").map {
        it.joinToString(" ").formatMini()
    }.let { argument ->
        argument.setSuggestionCallback { _, _, suggestion ->
            val input = suggestion.input

            val endIndex = input.lastIndexOf('>')

            val startIndex = input.lastIndexOf('<').also {
                // < needs to appear for this to suggest
                if (it == -1) {
                    suggestion.addEntry(SuggestionEntry(input, input.formatMini()))
                    return@setSuggestionCallback
                }

                // > shouldn't appear after the last occurance of <
                if (endIndex > it) {
                    suggestion.addEntry(SuggestionEntry(input, input.formatMini()))
                    return@setSuggestionCallback
                }
            }

            if (when (input.indexOf("<pre>")) {
                    -1 -> Int.MAX_VALUE
                    else -> input.indexOf("<pre>")
                } < startIndex) {
                suggestion.addEntry(SuggestionEntry(input, input.formatMini()))
                return@setSuggestionCallback
            }

            (NamedTextColor.NAMES.keys().map(String::lowercase) + allMiniMessageTerms)
                .mapNotNull {
                    // Get the content of the input with <, ex <re will return re -- if the user hasnt typed any content no need to map
                    val content = contentRegex.find(input)?.value ?: return@mapNotNull it

                    // Continuing with re, if this is blue itll invalidate the blue suggestion, but red will continue
                    if (!it.startsWith(content)) return@mapNotNull null

                    // Drop "re" from red, returning d
                    return@mapNotNull it.drop(content.length)
                }
                .map { SuggestionEntry("$input$it>") }
                .also {
                    if (it.isEmpty())
                        suggestion.addEntry(SuggestionEntry(input, input.formatMini()))
                }
                .forEach(suggestion::addEntry)
        }
    }

    fun single(id: String) = ArgumentType.String(id).map {
        it.formatMini()
    }
}