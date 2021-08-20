package world.cepi.kstom.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.minestom.server.coordinate.Point

/**
 * Plays a sound to a specified [Audience]
 *
 * @param sound The sound to play
 * @param position The [Point] to play the sound at
 */
fun Audience.playSound(sound: Sound, position: Point) =
    playSound(sound, position.x(), position.y(), position.z())
