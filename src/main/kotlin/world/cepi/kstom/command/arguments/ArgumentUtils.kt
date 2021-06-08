package world.cepi.kstom.command.arguments

import com.google.common.annotations.Beta
import net.kyori.adventure.text.Component
import net.minestom.server.color.Color
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentLiteral
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.suggestion.Suggestion
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import net.minestom.server.entity.EntityType
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeBlockPosition
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.UpdateOption
import org.jetbrains.annotations.Contract
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import java.util.function.Supplier
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

/**
 * Automatically generates an [ArgumentLiteral] based on the String being passed
 *
 * @return an [ArgumentLiteral] based on the String being passed
 */
public fun String.literal(): ArgumentLiteral = ArgumentType.Literal(this)

public fun <T> Argument<T>.defaultValue(value: T): Argument<T> =
    this.setDefaultValue { value }

/**
 * Suggests a set of [SuggestionEntry]s.
 * Will automatically sort and filter entries to match with input
 *
 * @param lambda The lambda to process the args
 *
 * @return The argument that had its suggestion callback set
 */
@Beta
@Contract("_ -> this")
public fun <T> Argument<T>.suggest(
    lambda: (sender: CommandSender, context: CommandContext) -> MutableList<SuggestionEntry>
): Argument<T>
    = this.setSuggestionCallback { sender, context, suggestion ->

        lambda(sender, context)
            .filter { it.entry.startsWith(suggestion.input) }
            .sortedBy { it.entry }
            .forEach { suggestion.addEntry(it) }

    }
