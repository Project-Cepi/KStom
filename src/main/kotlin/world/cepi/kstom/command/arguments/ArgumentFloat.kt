package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.arguments.number.ArgumentNumber
import net.minestom.server.command.builder.exception.ArgumentSyntaxException

public class ArgumentFloat(id: String) : ArgumentNumber<Float>(id) {
    @Throws(ArgumentSyntaxException::class)
    override fun parse(input: String): Float {
        return try {
            var value: Float
            run {
                val parsed = parseValue(input)
                val radix = getRadix(input)
                value = if (radix != 10) {
                    parsed.toLong(radix).toFloat()
                } else {
                    parsed.toFloat()
                }
            }

            // Check range
            if (hasMin && value < min!!) {
                throw ArgumentSyntaxException("Input is lower than the minimum required value", input, RANGE_ERROR)
            }
            if (hasMax && value > max!!) {
                throw ArgumentSyntaxException("Input is higher than the minimum required value", input, RANGE_ERROR)
            }
            value
        } catch (e: NumberFormatException) {
            throw ArgumentSyntaxException("Input is not a number/long", input, NOT_NUMBER_ERROR)
        } catch (e: NullPointerException) {
            throw ArgumentSyntaxException("Input is not a number/long", input, NOT_NUMBER_ERROR)
        }
    }

    init {
        min = Float.MIN_VALUE
        max = Float.MAX_VALUE
    }
}