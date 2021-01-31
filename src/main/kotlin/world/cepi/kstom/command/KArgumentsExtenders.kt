package world.cepi.kstom.command

import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.arguments.*
import net.minestom.server.command.builder.arguments.minecraft.*
import net.minestom.server.command.builder.arguments.minecraft.registry.*
import net.minestom.server.command.builder.arguments.number.ArgumentDouble
import net.minestom.server.command.builder.arguments.number.ArgumentFloat
import net.minestom.server.command.builder.arguments.number.ArgumentInteger
import net.minestom.server.command.builder.arguments.number.ArgumentLong
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec2
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3

fun KArguments.string(name: String) = ArgumentString(name).also(argumentList::add)
fun KArguments.integer(name: String) = ArgumentInteger(name).also(argumentList::add)
fun KArguments.float(name: String) = ArgumentFloat(name).also(argumentList::add)
fun KArguments.double(name: String) = ArgumentDouble(name).also(argumentList::add)
fun KArguments.long(name: String) = ArgumentLong(name).also(argumentList::add)
fun KArguments.boolean(name: String) = ArgumentBoolean(name).also(argumentList::add)
fun KArguments.stringArray(name: String) = ArgumentStringArray(name).also(argumentList::add)
fun KArguments.word(name: String, vararg words: String) = ArgumentWord(name).from(*words).also(argumentList::add)
fun KArguments.dynamicWord(name: String, suggestionType: SuggestionType) = ArgumentDynamicWord(name, suggestionType).also(argumentList::add)
fun KArguments.dynamicStringArray(name: String) = ArgumentDynamicStringArray(name).also(argumentList::add)
fun KArguments.color(name: String) = ArgumentColor(name).also(argumentList::add)
fun KArguments.entity(name: String) = ArgumentEntity(name).also(argumentList::add)
fun KArguments.floatRange(name: String) = ArgumentFloatRange(name).also(argumentList::add)
fun KArguments.intRange(name: String) = ArgumentIntRange(name).also(argumentList::add)
fun KArguments.itemStack(name: String) = ArgumentItemStack(name).also(argumentList::add)
fun KArguments.nbtCompoundTag(name: String) = ArgumentNbtCompoundTag(name).also(argumentList::add)
fun KArguments.nbtTag(name: String) = ArgumentNbtTag(name).also(argumentList::add)
fun KArguments.time(name: String) = ArgumentTime(name).also(argumentList::add)
fun KArguments.blockState(name: String) = ArgumentBlockState(name).also(argumentList::add)
fun KArguments.enchantment(name: String) = ArgumentEnchantment(name).also(argumentList::add)
fun KArguments.entityType(name: String) = ArgumentEntityType(name).also(argumentList::add)
fun KArguments.particle(name: String) = ArgumentParticle(name).also(argumentList::add)
fun KArguments.potionEffect(name: String) = ArgumentPotionEffect(name).also(argumentList::add)
fun KArguments.relativeBlockPosition(name: String) = ArgumentRelativeBlockPosition(name).also(argumentList::add)
fun KArguments.relativeVec2(name: String) = ArgumentRelativeVec2(name).also(argumentList::add)
fun KArguments.relativeVec3(name: String) = ArgumentRelativeVec3(name).also(argumentList::add)

fun <T: KArguments> T.withArguments(arguments: Arguments) = apply { this.arguments = arguments }