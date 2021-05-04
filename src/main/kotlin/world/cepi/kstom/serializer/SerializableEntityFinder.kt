package world.cepi.kstom.serializer

import kotlinx.serialization.Serializable
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity
import net.minestom.server.utils.entity.EntityFinder

@Serializable
class SerializableEntityFinder(
    val entityFinderString: String
) {
    fun get(): EntityFinder =
        ArgumentEntity.staticParse(entityFinderString, false, false)
}