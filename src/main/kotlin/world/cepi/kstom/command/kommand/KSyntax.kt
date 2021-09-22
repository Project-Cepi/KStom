package world.cepi.kstom.command.kommand

import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.entity.Player

class KSyntax(
    vararg val arguments: Argument<*>,
    val conditions: MutableList<Kommand.ConditionContext.() -> Boolean> = mutableListOf(),
    val kommandReference: Kommand
) {

    fun conditionPasses(context: Kommand.ConditionContext) = conditions.all { it(context) }

    fun condition(lambda: Kommand.ConditionContext.() -> Boolean): KSyntax {
        conditions += lambda
        return this
    }

    operator fun invoke(executor: Kommand.SyntaxContext.() -> Unit) {
        if (arguments.isEmpty()) {
            kommandReference.command.setDefaultExecutor { sender, context ->

                if (!conditionPasses(Kommand.ConditionContext(sender, context.input))) return@setDefaultExecutor

                executor(Kommand.SyntaxContext(sender, context))
            }

            return
        }

        kommandReference.command.addConditionalSyntax(
            { sender, string -> conditionPasses(Kommand.ConditionContext(sender, string ?: "")) },
            { sender, context -> executor(Kommand.SyntaxContext(sender, context)) },
            *arguments
        )

        return
    }

    val onlyPlayers: KSyntax get() = run {
        conditions += condition@ {
            if (sender !is Player) {
                kommandReference.playerCallbackFailMessage(sender)
                return@condition false
            }

            return@condition true
        }

        this
    }

    val onlyConsole: KSyntax get() = run {
        conditions += condition@ {
            if (sender !is ConsoleSender) {
                kommandReference.consoleCallbackFailMessage(sender)
                return@condition false
            }

            return@condition true
        }

        return this
    }
}