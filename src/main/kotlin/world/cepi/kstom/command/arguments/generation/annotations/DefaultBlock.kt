package world.cepi.kstom.command.arguments.generation.annotations

import net.minestom.server.instance.block.Block

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultBlock(val block: Block)
