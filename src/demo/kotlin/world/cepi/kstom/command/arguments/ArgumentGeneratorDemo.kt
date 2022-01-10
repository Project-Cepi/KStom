package world.cepi.kstom.command.arguments

import world.cepi.kstom.command.arguments.generation.ClassArgumentGenerator
import world.cepi.kstom.command.arguments.generation.ClassArgumentGenerator.Companion.syntaxesFrom
import world.cepi.kstom.command.arguments.generation.annotations.MaxAmount
import world.cepi.kstom.command.arguments.generation.annotations.MinAmount
import world.cepi.kstom.command.arguments.generation.argumentsFromClass
import world.cepi.kstom.command.kommand.Kommand

// The "old" version of this is manually doing it

data class CoolItem(
    val name: String,
    @param:MinAmount(5.0)
    @param:MaxAmount(6.0)
    val decimal: Double,
    val boolean: Boolean
)

object ArgumentGeneratorDemo : Kommand({
    syntaxesFrom<CoolItem> { instance ->
        sender.sendMessage(instance.toString())
    }

    val alternativeArgumentGenerator = ClassArgumentGenerator(CoolItem::class)

    alternativeArgumentGenerator.applySyntax(this) { instance ->
        sender.sendMessage(instance.toString())
    }
}, "argumentGeneratorDemo")

