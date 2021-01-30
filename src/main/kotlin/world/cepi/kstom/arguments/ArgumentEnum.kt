package world.cepi.kstom.arguments

import net.minestom.server.command.builder.arguments.ArgumentWord

/** ArgumentEnum that can hold a list of Enums. Meant to transfer the properties of other enum data towards a handler. */
class ArgumentEnum(id: String,
                   /** The list of enums to be used as internal storage. */
                   val enumArray: Array<Enum<*>>
) : ArgumentWord(id) {

    override fun from(vararg restrictions: String): ArgumentEnum {
        this.restrictions = restrictions
        return this
    }

}