package world.cepi.kstom.util

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec

operator fun Pos.component1() = this.x()
operator fun Pos.component2() = this.y()
operator fun Pos.component3() = this.z()
operator fun Pos.component4() = this.yaw()
operator fun Pos.component5() = this.pitch()

operator fun Point.component1() = this.x()
operator fun Point.component2() = this.y()
operator fun Point.component3() = this.z()

operator fun Vec.component1() = this.x()
operator fun Vec.component2() = this.y()
operator fun Vec.component3() = this.z()