package world.cepi.kstom.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec

/**
 * Plays a sound to a specified [Audience]
 *
 * @param sound The sound to play
 * @param position The [Pos] to play the sound at
 */
fun Audience.playSound(sound: Sound, position: Pos) =
    playSound(sound, position.x(), position.y(), position.z())

/**
 * Plays a sound to a specified [Audience]
 *
 * @param sound The sound to play
 * @param vector The [Vec] to play the sound at
 */
fun Audience.playSound(sound: Sound, vector: Vec) =
    playSound(sound, vector.x(), vector.y(), vector.z())
