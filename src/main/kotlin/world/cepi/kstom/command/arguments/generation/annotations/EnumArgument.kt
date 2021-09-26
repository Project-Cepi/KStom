package world.cepi.kstom.command.arguments.generation.annotations

import net.minestom.server.command.builder.arguments.ArgumentEnum

annotation class EnumArgument(
    val default: String,
    val flattenType: ArgumentEnum.Format
)
