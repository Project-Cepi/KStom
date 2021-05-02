package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.Command
import world.cepi.kstom.command.addSyntax

// The "old" version of this is manually doing it

data class CoolItem(
    val name: String,
    val decimal: Double,
    val boolean: Boolean
)

object ArgumentGeneratorDemo : Command("argumentGeneratorDemo") {

    init {
        val classArgs = argumentsFromClass<CoolItem>()
        val alternativeClassArgs = argumentsFromClass(CoolItem::class)

        addSyntax(*classArgs.args) { sender, args ->
            val obj = classArgs.createInstance(args)

            sender.sendMessage(obj.toString())
        }
    }

}