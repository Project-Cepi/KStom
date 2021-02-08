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
import world.cepi.kstom.command.arguments.ArgumentEnum
import world.cepi.kstom.command.arguments.ArgumentPlayer

public fun KArguments.string(name: String): ArgumentString = ArgumentString(name).also(argumentList::add)
public fun KArguments.integer(name: String): ArgumentInteger = ArgumentInteger(name).also(argumentList::add)
public fun KArguments.float(name: String): ArgumentFloat = ArgumentFloat(name).also(argumentList::add)
public fun KArguments.double(name: String): ArgumentDouble = ArgumentDouble(name).also(argumentList::add)
public fun KArguments.long(name: String): ArgumentLong = ArgumentLong(name).also(argumentList::add)
public fun KArguments.boolean(name: String): ArgumentBoolean = ArgumentBoolean(name).also(argumentList::add)
public fun KArguments.stringArray(name: String): ArgumentStringArray = ArgumentStringArray(name).also(argumentList::add)
public fun KArguments.word(name: String, vararg words: String): ArgumentWord = ArgumentWord(name).from(*words).also(argumentList::add)
public fun KArguments.dynamicWord(name: String, suggestionType: SuggestionType): ArgumentDynamicWord = ArgumentDynamicWord(name, suggestionType).also(argumentList::add)
public fun KArguments.dynamicStringArray(name: String): ArgumentDynamicStringArray = ArgumentDynamicStringArray(name).also(argumentList::add)
public fun KArguments.color(name: String): ArgumentColor = ArgumentColor(name).also(argumentList::add)
public fun KArguments.entity(name: String): ArgumentEntity = ArgumentEntity(name).also(argumentList::add)
public fun KArguments.floatRange(name: String): ArgumentFloatRange = ArgumentFloatRange(name).also(argumentList::add)
public fun KArguments.intRange(name: String): ArgumentIntRange = ArgumentIntRange(name).also(argumentList::add)
public fun KArguments.itemStack(name: String): ArgumentItemStack = ArgumentItemStack(name).also(argumentList::add)
public fun KArguments.nbtCompoundTag(name: String): ArgumentNbtCompoundTag = ArgumentNbtCompoundTag(name).also(argumentList::add)
public fun KArguments.nbtTag(name: String): ArgumentNbtTag = ArgumentNbtTag(name).also(argumentList::add)
public fun KArguments.time(name: String): ArgumentTime = ArgumentTime(name).also(argumentList::add)
public fun KArguments.blockState(name: String): ArgumentBlockState = ArgumentBlockState(name).also(argumentList::add)
public fun KArguments.enchantment(name: String): ArgumentEnchantment = ArgumentEnchantment(name).also(argumentList::add)
public fun KArguments.entityType(name: String): ArgumentEntityType = ArgumentEntityType(name).also(argumentList::add)
public fun KArguments.particle(name: String): ArgumentParticle = ArgumentParticle(name).also(argumentList::add)
public fun KArguments.potionEffect(name: String): ArgumentPotionEffect = ArgumentPotionEffect(name).also(argumentList::add)
public fun KArguments.relativeBlockPosition(name: String): ArgumentRelativeBlockPosition = ArgumentRelativeBlockPosition(name).also(argumentList::add)
public fun KArguments.relativeVec2(name: String): ArgumentRelativeVec2 = ArgumentRelativeVec2(name).also(argumentList::add)
public fun KArguments.relativeVec3(name: String): ArgumentRelativeVec3 = ArgumentRelativeVec3(name).also(argumentList::add)
public fun KArguments.player(name: String): ArgumentPlayer = ArgumentPlayer(name).also(argumentList::add)
public fun <T: Enum<T>> KArguments.enum(name: String, enumArray: Array<T>): ArgumentEnum<T> = ArgumentEnum<T>(name).from(*enumArray)
public fun <T: KArguments> T.withArguments(arguments: Arguments): T = apply { this.arguments = arguments }