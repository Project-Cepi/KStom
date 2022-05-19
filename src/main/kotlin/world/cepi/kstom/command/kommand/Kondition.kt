package world.cepi.kstom.command.kommand

import net.minestom.server.command.ConsoleSender
import net.minestom.server.entity.Player

abstract class Kondition<T : Kondition<T>> {
    abstract val conditions: MutableList<Kommand.ConditionContext.() -> Boolean>
    abstract val t: T
    abstract val kommandReference: Kommand

    fun conditionPasses(context: Kommand.ConditionContext) = conditions.all { it(context) }

    fun condition(lambda: Kommand.ConditionContext.() -> Boolean): T {
        conditions += lambda
        return t
    }

    fun onlyPlayers(): T {
        conditions += condition@{
            if (sender !is Player) {
                kommandReference.playerCallbackFailMessage(sender)
                return@condition false
            }

            return@condition true
        }

        return t
    }

    fun onlyConsole(): T {
        conditions += condition@{
            if (sender !is ConsoleSender) {
                kommandReference.consoleCallbackFailMessage(sender)
                return@condition false
            }

            return@condition true
        }

        return t
    }
}