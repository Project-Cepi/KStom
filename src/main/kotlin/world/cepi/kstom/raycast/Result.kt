package world.cepi.kstom.raycast

import net.minestom.server.entity.LivingEntity
import net.minestom.server.utils.Vector

data class Result(val finalPosition: Vector, val hitType: HitType, val hitEntity: LivingEntity?)
