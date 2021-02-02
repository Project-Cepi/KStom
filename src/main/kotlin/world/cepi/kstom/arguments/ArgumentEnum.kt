package world.cepi.kstom.arguments

import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import org.apache.commons.lang3.StringUtils

/** ArgumentEnum that can hold a list of Enums. Meant to transfer the properties of other enum data towards a handler. */
public class ArgumentEnum<T: Enum<T>>(id: String,
                                      /** The list of enums to be used as internal storage. */
                                      public var enumArray: Array<T>
) : Argument<T>(id, false, false) {
    public companion object {
        public const val SPACE_ERROR: Int = 1
        public const val RESTRICTION_ERROR: Int = 2
    }

    public fun from(vararg enums: T): ArgumentEnum<T> {
        @Suppress("UNCHECKED_CAST")
        this.enumArray = enums as Array<T>
        return this
    }

    override fun parse(input: String): T {
        if (StringUtils.SPACE in input) throw ArgumentSyntaxException(
            "Word cannot contain space character",
            input,
            SPACE_ERROR
        )

        return enumArray.firstOrNull { it.name.equals(input, true) }
            ?: throw ArgumentSyntaxException(
                "Word needs to be in the restriction list",
                input,
                RESTRICTION_ERROR
            )
    }
}