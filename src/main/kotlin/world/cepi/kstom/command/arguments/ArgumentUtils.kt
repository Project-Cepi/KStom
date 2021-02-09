package world.cepi.kstom.command.arguments

import net.minestom.server.chat.ChatColor
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.EntityType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.math.DoubleRange
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.TimeUnit
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters

/**
 * Automatically generates an ArgumentWord based on the ID
 *
 * @return an ArgumentWord based on the ID
 */
public fun ArgumentWord.asSubcommand(): ArgumentWord = this.from(this.id)

/**
 * Automatically generates an ArgumentWord based on the String being passed
 *
 * @return an ArgumentWord based on the String being passed
 */
public fun String.asSubcommand(): ArgumentWord = ArgumentType.Word(this).from(this)

/**
 * Can generate a list of Arguments from a class constructor.
 *
 * @param constructor The constructor to use to generate args from.
 *
 * @return A organized hashmap of arguments and its classifier
 */
public fun argumentsFromConstructor(constructor: KFunction<*>): List<Argument<*>> =
        constructor.valueParameters.map { argumentFromClass(it.type.classifier!! as KClass<*>)!! }

/**
 * Generates a Minestom argument based on the class
 *
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
public fun argumentFromClass(clazz: KClass<*>): Argument<*>? {

    if (clazz.simpleName == null) return null

    // TODO allow complex types

    when (clazz) {
        String::class -> return ArgumentType.String(clazz.simpleName!!)
        Int::class -> return ArgumentType.Integer(clazz.simpleName!!)
        Double::class -> return ArgumentType.Double(clazz.simpleName!!)
        Long::class -> return ArgumentType.Long(clazz.simpleName!!)
        ChatColor::class -> return ArgumentType.Color(clazz.simpleName!!)
        EntityType::class -> return ArgumentType.EntityType(clazz.simpleName!!)
        Material::class -> return ArgumentType.ItemStack(clazz.simpleName!!)
        Boolean::class -> return ArgumentType.Boolean(clazz.simpleName!!)
        Float::class -> return ArgumentType.Float(clazz.simpleName!!)
        ItemStack::class -> return ArgumentType.ItemStack(clazz.simpleName!!)
        NBTCompound::class -> return ArgumentType.NbtCompound(clazz.simpleName!!)
        NBT::class -> return ArgumentType.NBT(clazz.simpleName!!)
        TimeUnit::class -> return ArgumentType.Time(clazz.simpleName!!)
        IntRange::class -> return ArgumentType.IntRange(clazz.simpleName!!)
        FloatRange::class -> return ArgumentType.FloatRange(clazz.simpleName!!)
        else -> {
            if (clazz.java.enumConstants == null) return null

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            val enumConstraints =
                    clazz.java.enumConstants as Array<Enum<*>>

            return ArgumentEnum<Enum<*>>(clazz.simpleName!!).from(*enumConstraints)
        }
    }
}