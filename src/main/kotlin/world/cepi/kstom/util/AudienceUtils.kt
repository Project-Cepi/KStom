package world.cepi.kstom.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.minestom.server.coordinate.Point

fun Audience.playSound(sound: Sound, point: Point) =
    playSound(sound, point.x(), point.y(), point.z())