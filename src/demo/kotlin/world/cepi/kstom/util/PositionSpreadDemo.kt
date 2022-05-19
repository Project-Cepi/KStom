package world.cepi.kstom.util

import net.minestom.server.coordinate.Pos

fun old(position: Pos) {
    val x = position.x()
    val y = position.y()
    val z = position.z()
    val yaw = position.yaw
    val pitch = position.pitch
}

fun new(position: Pos) {
    val (x, y, z, yaw, pitch) = position
}