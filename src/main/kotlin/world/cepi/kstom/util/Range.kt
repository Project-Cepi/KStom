package world.cepi.kstom.util

import net.minestom.server.utils.math.DoubleRange
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import kotlin.random.Random

fun DoubleRange.random(): Double =
    Random.Default.nextDouble(minimum.toDouble(), maximum.toDouble())

fun FloatRange.random(): Float =
    Random.Default.nextDouble(minimum.toDouble(), maximum.toDouble()).toFloat()

fun IntRange.random(): Int =
  Fl  Random.Default.nextInt(minimum, maximum)