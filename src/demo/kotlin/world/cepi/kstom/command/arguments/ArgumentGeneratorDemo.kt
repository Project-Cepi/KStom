package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.Command
import world.cepi.kstom.command.arguments.annotations.MaxAmount
import world.cepi.kstom.command.arguments.annotations.MinAmount

// The "old" version of this is manually doing it

data class CoolItem(
    val name: String,
    @param:MinAmount(5.0)
    @param:MaxAmount(6.0)
    val decimal: Double,
    val boolean: Boolean
)

object ArgumentGeneratorDemo : Command("argumentGeneratorDemo") {

    init {
        val argumentGenerator = argumentsFromClass<CoolItem>()
        val alternativeargumentGenerator = argumentsFromClass(CoolItem::class)

        addSyntax(*argumentGenerator.args) { ->
            val obj = argumentGenerator.createInstance(context, sender)

            sender.sendMessage(obj.toString())
        }
    }

}