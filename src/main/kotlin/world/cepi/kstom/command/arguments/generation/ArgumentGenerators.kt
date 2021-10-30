package world.cepi.kstom.command.arguments.generation

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.color.Color
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandResult
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.potion.PotionEffect
import net.minestom.server.sound.SoundEvent
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.TimeUnit
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.Manager.command
import world.cepi.kstom.command.arguments.*
import world.cepi.kstom.command.arguments.generation.annotations.*
import world.cepi.kstom.command.arguments.context.ContextParser
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.kstom.serializer.SerializableEntityFinder
import world.cepi.kstom.tree.CombinationNode
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.time.Duration
import java.util.*
import java.util.function.Supplier
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

class GeneratedArguments<T : Any>(
    val clazz: KClass<T>,
    val args: List<List<Argument<*>>>
) {

    /**
     * The callback for all the arguments in this [GeneratedArguments].
     *
     * Setting this will set all the callbacks for every argument.
     */
    var callback: Kommand.ArgumentCallbackContext.() -> Unit = {  }
        set(value) {
            args.forEach { subArgs ->
                subArgs.forEach {
                    it.setCallback { sender, exception -> value(Kommand.ArgumentCallbackContext(sender, exception)) }
                }
            }
            field = value
        }

    init {
        CallbackGenerator.applyCallback(this)
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
    ) = args.forEach {
        command.syntax(*argumentsBefore, *it.toTypedArray(), *argumentsAfter) {
            val instance = createInstance(clazz, it.map { arg -> arg.id }, context, sender)

            lambda(this, instance)
        }
    }

    companion object {
        fun <T : Any> createInstance(
            clazzToGenerate: KClass<T>,
            currentArgs: List<String>,
            context: CommandContext,
            sender: CommandSender
        ): T {

            val constructor =
                clazzToGenerate.constructors.firstOrNull { it.hasAnnotation<GenerationConstructor>() }
                    ?: clazzToGenerate.primaryConstructor
                    ?: throw NullPointerException("Constructor is null, make sure the class has a constructor!")

            val classes = constructor.valueParameters.map {
                it.type.classifier as KClass<*>
            }

            val generatedArguments = currentArgs.mapIndexed { index, argumentName ->

                val value = context.get<Any>(argumentName)
                val clazz = classes[index]

                // Entity finder serializable exception
                if (value is EntityFinder) {
                    return@mapIndexed SerializableEntityFinder(context.getRaw(argumentName))
                }

                if (value is ArgumentContextValue<*>) {
                    return@mapIndexed value.from(sender)
                }

                if (value is Pair<*, *>) {

                    value as Pair<String, CommandContext>

                    return@mapIndexed createInstance(
                        clazz.sealedSubclasses.first { it.simpleName == value.first },
                        value.second.map.keys.toMutableList().also { it.removeAt(0) }, value.second,
                        sender
                    )
                }

                // Handle special context / sub edge cases
                when (value) {
                    is RelativeVec -> return@mapIndexed value.from(sender as? Entity)
                }

                return@mapIndexed value
            }

            try {
                return constructor.call(*generatedArguments.toTypedArray())
            } catch (exception: IllegalArgumentException) {
                // Print a more useful debug exception

                throw IllegalArgumentException("Expected types were $classes but received $generatedArguments; input: ${context.input}")
            }
        }

        inline fun <reified T: Any> Kommand.createSyntaxesFrom(
            vararg arguments: Argument<*>,
            noinline lambda: Kommand.SyntaxContext.(T) -> Unit
        ): GeneratedArguments<T> = generateSyntaxes<T>().also { it.applySyntax(this, arguments, emptyArray(), lambda) }

        fun <T : Any> Kommand.createSyntaxesFrom(
            clazz: KClass<T>,
            vararg arguments: Argument<*>,
            lambda: Kommand.SyntaxContext.(T) -> Unit
        ): GeneratedArguments<T> = generateSyntaxes(clazz).also { it.applySyntax(this, arguments, emptyArray(), lambda) }

        inline fun <reified T: Any> Kommand.createSyntaxesFrom(
            beforeArguments: Array<Argument<*>>,
            afterArguments: Array<Argument<*>>,
            noinline lambda: Kommand.SyntaxContext.(T) -> Unit
        ): GeneratedArguments<T> = generateSyntaxes<T>().also { it.applySyntax(this, beforeArguments, afterArguments, lambda) }

        fun <T: Any> Kommand.createSyntaxesFrom(
            clazz: KClass<T>,
            beforeArguments: Array<Argument<*>>,
            afterArguments: Array<Argument<*>>,
            lambda: Kommand.SyntaxContext.(T) -> Unit
        ): GeneratedArguments<T> = generateSyntaxes(clazz).also { it.applySyntax(this, beforeArguments, afterArguments, lambda) }

    }

}

/**
 * Can generate a list of Arguments from a class.
 *
 * @param T The class (type) to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws NullPointerException If the constructor or any arguments are invalid.
 */
inline fun <reified T : Any> generateSyntaxes(): GeneratedArguments<T> =
    generateSyntaxes(T::class)

/**
 * Can generate a list of Arguments from a class.
 *
 * @param clazz The class to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws NullPointerException If the constructor or any arguments are invalid.
 */
fun <T : Any> generateSyntaxes(clazz: KClass<T>): GeneratedArguments<T> {
    if (clazz.isSealed && clazz.sealedSubclasses.isNotEmpty()) {
        return GeneratedArguments(clazz, clazz.sealedSubclasses.map {
            argumentsFromFunction(it.primaryConstructor!!)
        }.flatten())
    }

    return GeneratedArguments(clazz, argumentsFromFunction(clazz.primaryConstructor!!))
}

/**
 * Can generate a list of Arguments from a function.
 *
 * Example:
 * Attack (sealed): (energy: Int) / (clickType: Enum)
 * (damage: Int, attack: Attack)
 *
 * Should generate:
 * (damage: Int, energy, energy: Int) / (damage: Int, clickType, clickType: Enum)
 *
 * @param function The function to generate args from.
 *
 * @return A list of lists; the first list is possible argument combinations. The second list is a list of arguments.
 */
fun argumentsFromFunction(function: KFunction<*>): List<List<Argument<*>>> {
    // list of all combinations ordered.
    // EX, if you have [damage: Int, energy, energy: Int] / [damage: Int, clickType, clickType: Enum],
    // the list would be: [[damage]], [[energy, energy: Int], [clickType, clickType: Int]]
    val args: List<Array<Argument<*>>> = function.valueParameters.mapIndexed { index, parameter ->

        val clazz = parameter.type.classifier!! as KClass<*>

        if (clazz.isSealed) {
            // list(list(energy, energy: Int), list(clickType, clickType: Int))
            return@mapIndexed clazz.sealedSubclasses.map { subClass ->
                ArgumentPrintableGroup(
                    subClass.simpleName!!,
                    arrayOf(
                        subClass.simpleName!!.replaceFirstChar { it.lowercase() }.literal(),
                        *argumentsFromFunction(subClass.primaryConstructor!!)
                            .flatten().toTypedArray()
                    )
                )

            }.toTypedArray() // energy (energy: Int) / clickType (clickType: Enum)
        }

        arrayOf(
            argumentFromClass(
                parameter.name ?: parameter.type.jvmErasure.simpleName!!,
                clazz,
                parameter.annotations,
                function.valueParameters.size - 1 == index
            )
        )

    }

    val rootNode = CombinationNode<Argument<*>>(ShellArgument) // empty node

    // list of ((damage)), ((energy, energy: Int), (clickType, clickType: Enum))
    // should turn into:
    // ((damage)): ((energy, energy: Int), (clickType, clickType: Enum))
    args.forEach {
        rootNode.addItemsToLastNodes(*it)
    }

    return rootNode.traverseAndGenerate()
}

/**
 * Generates a Minestom argument based on the class
 *
 * @param name The name of the argument
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
fun argumentFromClass(
    name: String,
    clazz: KClass<*>,
    annotations: List<Annotation> = emptyList(),
    isLast: Boolean
): Argument<*> {

    if (annotations.any { it is ParameterContext }) {
        val annotation = annotations.filterIsInstance<ParameterContext>().first()

        val instance = annotation.parser.objectInstance!! as ContextParser<*>

        return ArgumentContext(lambda = instance::parse)
    }

    return when (clazz) {
        String::class -> if (isLast)
            ArgumentType.StringArray(name).map { it.joinToString(" ") }
        else
            ArgumentType.String(name)
        Int::class -> ArgumentType.Integer(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min.toInt()) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max.toInt()) }
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toInt()) }
        }
        Double::class -> ArgumentType.Double(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max) }
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()?.let { argument.defaultValue(it.number) }
        }
        Color::class -> ArgumentType.Color(name)
        EntityType::class -> ArgumentType.EntityType(name)
        Material::class -> ArgumentMaterial(name)
        Boolean::class -> ArgumentType.Boolean(name).also { argument ->
            annotations.filterIsInstance<DefaultBoolean>().firstOrNull()
                ?.let { argument.defaultValue(it.boolean) }
        }
        Float::class -> ArgumentType.Float(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min.toFloat()) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max.toFloat()) }
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toFloat()) }
        }
        ItemStack::class, Material::class -> ArgumentType.ItemStack(name).also { argument ->
            annotations.filterIsInstance<DefaultMaterial>().firstOrNull()
                ?.let { argument.defaultValue(ItemStack.of(Material.fromNamespaceId(it.material) ?: error("Invalid Material ${it.material}!"))) }
        }
        NBTCompound::class -> ArgumentType.NbtCompound(name)
        NBT::class -> ArgumentType.NBT(name)
        Component::class -> ArgumentType.Component(name)
        Duration::class -> ArgumentType.Time(name).also { argument ->
            annotations.filterIsInstance<DefaultChronoDuration>().firstOrNull()
                ?.let { argument.defaultValue(Duration.of(it.amount, it.timeUnit)) }

            annotations.filterIsInstance<DefaultTickDuration>().firstOrNull()
                ?.let {
                    argument.defaultValue(
                        Duration.of(
                            it.amount,
                            if (it.isClient) TimeUnit.CLIENT_TICK else TimeUnit.SERVER_TICK
                        )
                    )
                }
        }
        IntRange::class -> ArgumentType.IntRange(name)
        FloatRange::class -> ArgumentType.FloatRange(name)
        SerializableEntityFinder::class -> ArgumentType.Entity(name)
        Enchantment::class -> ArgumentType.Enchantment(name)
        RelativeVec::class -> ArgumentType.RelativeVec3(name)
        Vec::class -> ArgumentType.RelativeVec3(name)
        SoundEvent::class -> ArgumentSound(name)
        Byte::class -> ArgumentByte(name).also { argument ->
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toInt().toByte().coerceAtLeast(Byte.MIN_VALUE).coerceAtMost(Byte.MAX_VALUE)) }

        }
        Block::class -> ArgumentType.BlockState(name).also { argument ->
            annotations.filterIsInstance<DefaultBlock>().firstOrNull()
                ?.let { argument.defaultValue(Block.fromNamespaceId(it.block)) }
        }
        Point::class -> ArgumentType.RelativeVec3(name)
        CommandResult::class -> ArgumentType.Command(name)
        PotionEffect::class -> ArgumentType.Potion(name).also { argument ->
            annotations.filterIsInstance<DefaultPotionEffect>().firstOrNull()
                ?.let { argument.defaultValue(PotionEffect.fromNamespaceId(it.potionEffect)) }
        }
        UUID::class -> ArgumentType.UUID(name)
        else -> {

            if (clazz.java.enumConstants == null) throw IllegalStateException("Class ${clazz.qualifiedName} Must be a valid argument!")

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            return (ArgumentEnum(name, clazz.java as Class<Enum<*>>)).also { enumArgument ->
                val annotation = annotations.filterIsInstance<EnumArgument>().firstOrNull() ?: return@also

                val enumConstraints = (clazz.java as Class<Enum<*>>).enumConstants

                enumArgument.setFormat(annotation.flattenType)
                enumArgument.defaultValue(enumConstraints.firstOrNull { it.name.lowercase() == annotation.default.lowercase() })
            }
        }
    }
}