package world.cepi.kstom.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.minestom.server.adventure.audience.PacketGroupingAudience
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player

/**
 * Plays a sound to a specified [Audience]
 *
 * @param sound The sound to play
 * @param position The [Point] to play the sound at
 */
fun Audience.playSound(sound: Sound, position: Point) =
    playSound(sound, position.x(), position.y(), position.z())

val Entity.viewersAndSelf get() = listOf(*viewers.toTypedArray(), this as? Player).filterNotNull()
val Entity.viewersAndSelfAsAudience: PacketGroupingAudience get() = PacketGroupingAudience.of(this.viewers.let { if (this is Player) it + this else it })
fun Entity.playSoundToViewersAndSelf(sound: Sound, position: Point = this.position) = viewersAndSelfAsAudience.playSound(sound, position)
fun Entity.playSoundToViewersAndSelf(sound: Sound, x: Double, y: Double, z: Double) = playSoundToViewersAndSelf(sound, Vec(x, y, z))

fun Audience.sendMessage(message: String) = this.sendMessage(Component.text(message))
