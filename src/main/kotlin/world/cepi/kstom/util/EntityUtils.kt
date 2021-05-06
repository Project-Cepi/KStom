package world.cepi.kstom.util

import net.minestom.server.entity.Entity
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position
import net.minestom.server.utils.entity.EntityUtils
import java.util.function.Consumer

public fun Instance.forEachRange(position: Position, viewDistance: Int, consumer: Consumer<Entity>): Unit =
    EntityUtils.forEachRange(this, position, viewDistance, consumer)

public fun Entity.isVisibleTo(other: Entity): Boolean = EntityUtils.areVisible(this, other)
