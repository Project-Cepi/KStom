package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.builder.arguments.Argument
import world.cepi.kstom.command.kommand.Kommand
import kotlin.reflect.KClass

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
        vararg argumentsBefore: Argument<*>,
        lambda: Kommand.SyntaxContext.(T) -> Unit
    ) = applySyntax(command, argumentsBefore, emptyArray(), lambda)

    @JvmName("arrayApplySyntax")
    fun applySyntax(
        command: Kommand,
        argumentsBefore: Array<out Argument<*>>,
        argumentsAfter: Array<out Argument<*>>,
        lambda: Kommand.SyntaxContext.(T) -> Unit
    ) = arguments.forEachIndexed { index, it ->
        command.syntax(*argumentsBefore, *it.toTypedArray(), *argumentsAfter) {
            val instance = generate(Kommand.SyntaxContext(sender, context), it.map { it.id }, index)

            lambda(this, instance)
        }
    }

    abstract fun generate(syntax: Kommand.SyntaxContext, args: List<String>, index: Int): T
}