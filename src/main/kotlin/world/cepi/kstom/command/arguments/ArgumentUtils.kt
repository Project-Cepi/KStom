package world.cepi.kstom.command.arguments

import net.minestom.server.chat.ChatColor
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.EntityType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.TimeUnit
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

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
        constructor.valueParameters.map { argumentFromClass(it.name ?: it.type.jvmErasure.simpleName!!, it.type.classifier!! as KClass<*>)!! }

/**
 * Generates a Minestom argument based on the class
 *
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
public fun argumentFromClass(name: String, clazz: KClass<*>): Argument<*>? {

    if (clazz.simpleName == null) return null

    // TODO allow complex types

    when (clazz) {
        String::class -> return ArgumentType.String(name)
        Int::class -> return ArgumentType.Integer(name)
        Double::class -> return ArgumentType.Double(name)
        Long::class -> return ArgumentType.Long(name)
        ChatColor::class -> return ArgumentType.Color(name)
        EntityType::class -> return ArgumentType.EntityType(name)
        Material::class -> return ArgumentType.ItemStack(name)
        Boolean::class -> return ArgumentType.Boolean(name)
        Float::class -> return ArgumentType.Float(name)
        ItemStack::class -> return ArgumentType.ItemStack(name)
        NBTCompound::class -> return ArgumentType.NbtCompound(name)
        NBT::class -> return ArgumentType.NBT(name)
        TimeUnit::class -> return ArgumentType.Time(name)
        IntRange::class -> return ArgumentType.IntRange(name)
        FloatRange::class -> return ArgumentType.FloatRange(name)
        else -> {
            if (clazz.java.enumConstants == null) return null

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            val enumConstraints =
                    clazz.java.enumConstants as Array<Enum<*>>

            return ArgumentEnum<Enum<*>>(name).from(*enumConstraints)
        }
    }
}