package world.cepi.kstom.util

import net.minestom.server.collision.BoundingBox
import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Entity

@JvmName("intersectAnyBoundingBoxes")
fun BoundingBox.intersectAny(boundingBoxes: Collection<BoundingBox>): Boolean =
    boundingBoxes.any { it.intersect(this) }

@JvmName("intersectAnyPoints")
fun BoundingBox.intersectAny(points: Collection<Point>): Boolean =
    points.any { this.intersect(it) }

@JvmName("intersectAnyEntities")
fun BoundingBox.intersectAny(entities: Collection<Entity>): Boolean =
    entities.any { this.intersect(it) }


@JvmName("intersectAllBoundingBoxes")
fun BoundingBox.intersectAll(boundingBoxes: Collection<BoundingBox>): Boolean =
    boundingBoxes.all { it.intersect(this) }

@JvmName("intersectAllPoints")
fun BoundingBox.intersectAll(points: Collection<Point>): Boolean =
    points.all { this.intersect(it) }

@JvmName("intersectAllEntities")
fun BoundingBox.intersectAll(entities: Collection<Entity>): Boolean =
    entities.all { this.intersect(it) }