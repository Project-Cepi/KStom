package world.cepi.kstom.util

import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.Position
import net.minestom.server.utils.Vector

operator fun Position.component1() = this.x
operator fun Position.component2() = this.y
operator fun Position.component3() = this.z
operator fun Position.component4() = this.yaw
operator fun Position.component5() = this.pitch

operator fun Vector.component1() = this.x
operator fun Vector.component2() = this.y
operator fun Vector.component3() = this.z

operator fun BlockPosition.component1() = this.x
operator fun BlockPosition.component2() = this.y
operator fun BlockPosition.component3() = this.z