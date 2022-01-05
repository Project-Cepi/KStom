package world.cepi.kstom.util

import net.minestom.server.extensions.Extension

val Extension.log get() = logger()
val Extension.node get() = eventNode()