package world.cepi.kstom.command.arguments

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentRegistry
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket
import net.minestom.server.registry.Registries
import net.minestom.server.sound.SoundEvent

/**
 * Represents an argument giving an [Sound.Type].
 */
class ArgumentSound(id: String?) : ArgumentRegistry<Sound.Type>(id) {
    override fun getRegistry(value: String): Sound.Type {
        return SoundEvent.fromNamespaceId(value)
            ?: return Sound.Type { Key.key(value) } // Instance of Sound.Type
    }

    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {
        val argumentNode: DeclareCommandsPacket.Node = simpleArgumentNode(this, executable, false, true)
        argumentNode.parser = "minecraft:resource_location"
        argumentNode.suggestionsType = SuggestionType.AVAILABLE_SOUNDS.identifier
        nodeMaker.addNodes(arrayOf(argumentNode))
    }

    override fun toString() = "Sound$id"

}