package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.entity.Entity
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeVec
import world.cepi.kstom.command.arguments.ArgumentContextValue
import world.cepi.kstom.command.arguments.ShellArgument.setCallback
import world.cepi.kstom.command.arguments.generation.annotations.GenerationConstructor
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.kstom.serializer.SerializableEntityFinder
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters

/**
 * The
 */
abstract class ArgumentGenerator<T : Any>(
    open val clazz: KClass<T>,
    val arguments: List<List<Argument<*>>>
) {

    init {
        CallbackGenerator.applyCallback(this)
    }

    var callback: Kommand.ArgumentCallbackContext.() -> Unit = {  }
        set(value) {
            arguments.forEach { subArgs ->
                subArgs.forEach {
                    it.setCallback { sender, exception -> value(Kommand.ArgumentCallbackContext(sender, exception)) }
                }
            }
            field = value
        }

    fun applySyntax(
        command: Kommand,
        vararg arguments: Argument<*>,
        lambda: Kommand.SyntaxContext.(T) -> Unit
    ) = applySyntax(command, arguments, emptyArray(), lambda)

    @JvmName("arrayApplySyntax")
    fun applySyntax(
        command: Kommand,
        argumentsBefore: Array<out Argument<*>>,
        argumentsAfter: Array<out Argument<*>>,
        lambda: Kommand.SyntaxContext.(T) -> Unit
    ) = arguments.forEach {
        command.syntax(*argumentsBefore, *it.toTypedArray(), *argumentsAfter) {
            val instance = generate(Kommand.SyntaxContext(sender, context), it.map { it.id })

            lambda(this, instance)
        }
    }

    abstract fun generate(syntax: Kommand.SyntaxContext, args: List<String>): T
}