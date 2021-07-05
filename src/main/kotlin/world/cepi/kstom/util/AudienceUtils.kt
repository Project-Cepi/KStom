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

/**
 * Plays a sound to a specified [Audience]
 *
 * @param sound The sound to play
 * @param blockPosition The block position to play at
 * @param absolute If true, don't add 0.5 to the end result (0.5 centers the position to the middle of the block pos.
 */
fun Audience.playSound(sound: Sound, blockPosition: BlockPosition, absolute: Boolean = false) =
    playSound(
        sound,
        blockPosition.x.toDouble() + if (absolute) 0.0 else 0.5,
        blockPosition.y.toDouble() + if (absolute) 0.0 else 0.5,
        blockPosition.z.toDouble() + if (absolute) 0.0 else 0.5
    )