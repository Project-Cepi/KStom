package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.builder.arguments.Argument

interface CustomArgumentGenerator {

    fun new(id: String, annotations: List<Annotation>): Argument<*>

}