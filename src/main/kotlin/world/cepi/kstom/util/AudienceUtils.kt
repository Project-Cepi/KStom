package world.cepi.kstom.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.Position
import net.minestom.server.utils.Vector

fun Audience.playSound(sound: Sound, position: Position) =
    playSound(sound, position.x, position.y, position.z)

fun Audience.playSound(sound: Sound, vector: Vector) =
    playSound(sound, vector.x, vector.y, vector.z)

fun Audience.playSound(sound: Sound, blockPosition: BlockPosition) =
    playSound(sound, blockPosition.x.toDouble(), blockPosition.y.toDouble(), blockPosition.z.toDouble())