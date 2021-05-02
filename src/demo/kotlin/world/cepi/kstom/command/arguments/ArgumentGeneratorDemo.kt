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
        val argumentGenerator = argumentsFromClass<CoolItem>()
        val alternativeargumentGenerator = argumentsFromClass(CoolItem::class)

        addSyntax(*argumentGenerator.args) { sender, args ->
            val obj = argumentGenerator.createInstance(args)

            sender.sendMessage(obj.toString())
        }
    }

}