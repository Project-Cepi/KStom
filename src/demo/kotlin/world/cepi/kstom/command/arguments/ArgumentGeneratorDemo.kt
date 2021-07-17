package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.Command
import world.cepi.kstom.command.arguments.generation.GeneratedArguments.Companion.createSyntaxesFrom
import world.cepi.kstom.command.arguments.generation.annotations.MaxAmount
import world.cepi.kstom.command.arguments.generation.annotations.MinAmount
import world.cepi.kstom.command.arguments.generation.generateSyntaxes

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
        createSyntaxesFrom<CoolItem> { instance ->
            sender.sendMessage(instance.toString())
        }

        val alternativeArgumentGenerator = generateSyntaxes(CoolItem::class)

        alternativeArgumentGenerator.applySyntax(this) { instance ->
            sender.sendMessage(instance.toString())
        }
    }

}