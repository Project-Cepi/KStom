package world.cepi.kstom.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.minestom.server.adventure.audience.PacketGroupingAudience
import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player

/**
 * Plays a sound to a specified [Audience]
 *
 * @param sound The sound to play
 * @param position The [Point] to play the sound at
 */
fun Audience.playSound(sound: Sound, position: Point) =
    playSound(sound, position.x(), position.y(), position.z())

val Player.viewersAndSelf get() = listOf(*viewers.toTypedArray(), this)
val Player.viewersAndSelfAsAudience get() = PacketGroupingAudience.of(viewersAndSelf);

fun Audience.sendMessage(message: String) = this.sendMessage(Component.text(message))
