package world.cepi.kstom.util

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import kotlin.math.PI

operator fun Pos.component4() = this.yaw()
operator fun Pos.component5() = this.pitch()

operator fun Point.component1() = this.x()
operator fun Point.component2() = this.y()
operator fun Point.component3() = this.z()

operator fun Entity.component1() = this.position.x()
operator fun Entity.component2() = this.position.y()
operator fun Entity.component3() = this.position.z()

val Point.x get() = this.x()
val Point.y get() = this.y()
val Point.z get() = this.z()

fun Point.asVec(): Vec = Vec(this.x(), this.y(), this.z())
fun Point.asPos(): Pos = Pos(this)

fun Pos.roundToBlock(): Pos = Pos(this.blockX().toDouble(), this.blockY().toDouble(), this.blockZ().toDouble(), this.yaw, this.pitch)
fun Vec.roundToBlock(): Vec = Vec(this.blockX().toDouble(), this.blockY().toDouble(), this.blockZ().toDouble())

fun Vec.rotateAroundXDegrees(degrees: Double) = rotateAroundX(degrees * (PI/180))
fun Vec.rotateAroundYDegrees(degrees: Double) = rotateAroundY(degrees * (PI/180))
fun Vec.rotateAroundZDegrees(degrees: Double) = rotateAroundZ(degrees * (PI/180))

fun Vec.rotateDegrees(degreesX: Double, degreesY: Double, degreesZ: Double) =
    rotate(degreesX * (PI/180), degreesY * (PI/180), degreesZ * (PI/180))

// Division is not needed as it already works
operator fun Point.plus(other: Point) = this.add(other)
operator fun Point.minus(other: Point) = this.add(other)
operator fun Point.times(other: Point) = this.add(other)

operator fun Vec.unaryMinus() = this.neg()
operator fun Vec.unaryPlus() = this.abs()